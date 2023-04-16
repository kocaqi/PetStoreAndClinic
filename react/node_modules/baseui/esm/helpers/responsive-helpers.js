/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/

/**
 * Helper function that generates media queries based on breakpoint, e.g.
 * getMediaQuery(720) => '@media screen and (min-width: 720px)'
 */
export var getMediaQuery = function getMediaQuery(breakpoint) {
  return "@media screen and (min-width: ".concat(breakpoint, "px)");
};
export var getMediaQueries = function getMediaQueries(breakpoints) {
  return Object.keys(breakpoints).map(function (key) {
    return breakpoints[key];
  }).sort(function (a, b) {
    return a - b;
  }).map(getMediaQuery);
};