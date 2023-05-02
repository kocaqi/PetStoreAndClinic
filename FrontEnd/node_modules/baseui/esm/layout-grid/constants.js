var _Object$freeze;

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/
export var BEHAVIOR = Object.freeze({
  fluid: 'fluid',
  fixed: 'fixed'
});
export var ALIGNMENT = Object.freeze({
  start: 'flex-start',
  center: 'center',
  end: 'flex-end'
});
export var STYLE = Object.freeze({
  default: 'default',
  compact: 'compact'
});
export var STYLE_VALUES = Object.freeze((_Object$freeze = {}, _defineProperty(_Object$freeze, STYLE.default, null), _defineProperty(_Object$freeze, STYLE.compact, {
  columns: [4, 8, 12],
  gutters: [16, 16, 16],
  margins: [16, 24, 24],
  gaps: 0,
  unit: 'px',
  maxWidth: 1280
}), _Object$freeze));