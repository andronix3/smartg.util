/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
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
 *  o Neither the name of imagero Andrey Kuznetsov nor the names of
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
package com.imagero.java.event;




/**
 *
 * @author andrey
 */
public class EventSource {
    protected final EventSource parentSource;

    final Object source;
    VEventListener vListener;
    BEventListener bListener;


    public EventSource() {
        this(null, null);
    }

    public EventSource(EventSource parent) {
        this(parent, null);
    }

    public EventSource(Object source) {
        this(null, source);
    }

    public EventSource(EventSource parent, Object source) {
        this.parentSource = parent;
        this.source = source;
    }

    public void addEventListener(VEventListener l) {
        if (parentSource == null) {
            if (vListener == null) {
                vListener = l;
            }
            else {
                vListener.addListener(l);
            }
        }
        else {
            parentSource.addEventListener(l);
        }
    }

    public void addEventListener(BEventListener l) {
        if (parentSource == null) {
            if (bListener == null) {
                bListener = l;
            }
            else {
                bListener.addListener(l);
            }
        }
        else {
            parentSource.addEventListener(l);
        }
    }

    public void removeEventListener(VEventListener l) {
        if (parentSource == null) {
            if (vListener != null) {
                vListener.removeListener(l);
            }
        }
        else {
            parentSource.removeEventListener(l);
        }
    }

    public void removeEventListener(BEventListener l) {
        if (parentSource == null) {
            if (bListener != null) {
                bListener.removeListener(l);
            }
        }
        else {
            parentSource.removeEventListener(l);
        }
    }

    protected void post(VEvent e) {
        if (parentSource == null) {
            if (vListener != null) {
                vListener.post(e);
            }
        }
        else {
            parentSource.post(e);
        }
    }

    protected void post(VEvent e, Object command) {
        if (parentSource == null) {
            if (vListener != null) {
                vListener.post(e, command);
            }
        }
        else {
            parentSource.post(e, command);
        }
    }

    protected boolean post(BEvent e, boolean defaultValue) {
        if (parentSource == null) {
            if (bListener != null) {
                return bListener.post(e);
            }
            return defaultValue;
        }
	return parentSource.post(e, defaultValue);
    }

    public final Object getSource() {
        return source;
    }
}
