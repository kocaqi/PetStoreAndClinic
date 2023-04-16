/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/
export function toPopperPlacement(placement) {
  return placement.replace(/(Top|Left)$/, '-start').replace(/(Right|Bottom)$/, '-end');
}
/**
 * Takes the offset passed from popper.js and normalizes it
 */

export function parsePopperOffset(offset) {
  return {
    top: Math.floor(offset.top || 0),
    left: Math.floor(offset.left || 0)
  };
}