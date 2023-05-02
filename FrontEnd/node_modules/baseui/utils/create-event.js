"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = createEvent;

/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/

/* global window document */

/** A safe way to create event objects down to IE11 */
function createEvent(eventName) {
  var event;

  if (typeof window.Event === 'function') {
    event = new window.Event(eventName, {
      bubbles: true,
      cancelable: true
    });
  } else {
    event = document.createEvent('Event');
    event.initEvent(eventName, true, true);
  }

  return event;
}