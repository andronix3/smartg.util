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
 * Class for listening to Events.
 *
 * @see BEventListener
 * @see VEventListener
 * @author andrey
 */
public abstract class EventListener {
    protected int eventID;
    protected int subId = -1;
    protected Object command;

    protected EventListener next;

    protected EventListener(Class<? extends Event> eventClass) {
        this.eventID = eventClass.getName().hashCode();
    }

    protected EventListener(Class<? extends Event> eventClass, int subId) {
        this.eventID = eventClass.getName().hashCode();
        this.subId = subId;
    }

    protected EventListener(Class<? extends Event> eventClass, Object command) {
        this.eventID = eventClass.getName().hashCode();
        this.command = command;
    }

    final void addListener(EventListener l) {
        if(l == null || l.equals(this)) {
            return;
        }
        if (next == null) {
            next = l;
        } else {
            next.addListener(l);
        }
    }

    final void removeListener(EventListener l) {
        if (next == null) {
            return;
        }
        if (next == l) {
            next = next.next;
        } else {
            next.removeListener(l);
        }
    }

    public final int getSubId() {
        return subId;
    }
}
