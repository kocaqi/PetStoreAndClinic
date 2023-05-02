"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.camelToKebab = void 0;

/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/
var camelToKebab = function camelToKebab(s) {
  return s.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
};

exports.camelToKebab = camelToKebab;