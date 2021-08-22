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
 *  ProxyEventSource just redirects event processing to supplied EventSource.
 *
 * @author Andrey Kuznetsov
 */
public class ProxyEventSource extends EventSource {
    protected EventSource eventSource;

    public ProxyEventSource(EventSource eventSource) {
        super(eventSource);
        this.eventSource = eventSource;
    }

    public void addEventListener(VEventListener l) {
        eventSource.addEventListener(l);
    }

    public void removeEventListener(VEventListener l) {
        eventSource.removeEventListener(l);
    }

    public void addEventListener(BEventListener l) {
        eventSource.addEventListener(l);
    }

    public void removeEventListener(BEventListener l) {
        eventSource.removeEventListener(l);
    }

    public void post(VEvent e) {
        eventSource.post(e);
    }

    public boolean post(BEvent e, boolean defaultValue) {
        return super.post(e, defaultValue);
    }

    public void post(VEvent e, Object command) {
        super.post(e, command);
    }
}
