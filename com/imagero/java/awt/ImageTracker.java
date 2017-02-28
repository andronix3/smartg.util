/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
 *
 * http://www.imagero.com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of Andrey Kuznetsov nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.imagero.java.awt;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.Timer;

/**
 * ImageTracker is a replacement for MediaTracker.
 * <pre>
 * Differences:
 *  1. easy to use
 *  2. synchronization is hidden inside
 *  3. designed to be thread safe
 *  4. doesn't uses Component to load images.
 * </pre>
 */
public class ImageTracker {

    public ImageTracker() {
    }

    /**
     * Check if image already loaded
     */
    protected boolean isReady(int state) {
        return (state & ImageObserver.ALLBITS) == ImageObserver.ALLBITS;
    }

    protected boolean isErroredOrAborted(int state) {
        return ((state & ImageObserver.ERROR) == ImageObserver.ERROR)
                || ((state & ImageObserver.ABORT) == ImageObserver.ABORT);
    }

    protected int getState(Image img, ImageObserver observer) {
        return Toolkit.getDefaultToolkit().checkImage(img, -1, -1, observer);
    }

    /**
     * Start image loading (if not already loaded)
     * @param img Imager to load
     * @param observer ImageObserver for notifications about Image constructing
     * @return true if Image is fully loaded
     */
    private boolean prepare(Image img, ImageObserver observer) {
        return Toolkit.getDefaultToolkit().prepareImage(img, -1, -1, observer);
    }

    /**
     * Load supplied image. If Image already loaded this method returns immediately.
     * @param img image to load
     * @param timeout if timeout is greater then 0, then method returns after specified timeout even if image is not yet loaded.
     * @return true if image fully loaded
     */
    public int loadImage(Image img, int timeout) {
        Loader loader = new Loader(this);
        IObserver observer = new IObserver(loader);
        int state = getState(img, observer);
        if (isDone(state)) {
            return state & (ImageObserver.ALLBITS | ImageObserver.ERROR | ImageObserver.ABORT);
        }
        try {
            return loader.load(img, observer, timeout);
        } catch (InterruptedException e) {
            //ignore
        }
        state = getState(img, observer);
        return state & (ImageObserver.ALLBITS | ImageObserver.ERROR | ImageObserver.ABORT);
    }

    protected boolean isDone(int state) {
        return isReady(state) || isErroredOrAborted(state);
    }

    private static class TimerListener implements ActionListener {
        Thread t;

        public TimerListener(Thread t) {
            this.t = t;
        }

        public void actionPerformed(ActionEvent e) {
            t.interrupt();
        }
    }

    static class IObserver implements ImageObserver {
        private Loader loader;

        public IObserver(Loader loader) {
            this.loader = loader;
        }

        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            if (infoflags == ALLBITS) {
                synchronized (loader) {
                    loader.notify();
                }
                return false;
            }
            return true;
        }
    }

    /**
     * Important - synchronize on Loader object - not on ImageTracker
     */
    static class Loader {

        ImageTracker tracker;

        public Loader(ImageTracker tracker) {
            this.tracker = tracker;
        }

        int load(Image img, ImageObserver observer, int timeout) throws InterruptedException {
            boolean ready = tracker.prepare(img, observer);
            if (!ready && timeout > 0) {
                Timer timer = new Timer(timeout, new TimerListener(Thread.currentThread()));
                timer.setRepeats(false);
                timer.start();
            }

            int state = 0;
            while (!tracker.isDone(state)) {
                synchronized (this) {
                    wait();
                }
                state = tracker.getState(img, observer);
            }
            return state;
        }
    }
}
