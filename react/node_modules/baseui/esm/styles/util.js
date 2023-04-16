/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/
export function hexToRgb() {
  var hex = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '';
  var alpha = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : '1';
  var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
  hex = hex.replace(shorthandRegex, function (m, r, g, b) {
    return r + r + g + g + b + b;
  });
  var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
  return result ? "rgba(".concat(parseInt(result[1], 16), ", ").concat(parseInt(result[2], 16), ", ").concat(parseInt(result[3], 16), ", ").concat(alpha, ")") : null;
}
export var ellipsisText = {
  overflow: 'hidden',
  textOverflow: 'ellipsis',
  whiteSpace: 'nowrap',
  wordWrap: 'normal'
};
export function expandBorderStyles(borderStyles) {
  return {
    borderTopWidth: borderStyles.borderWidth,
    borderTopStyle: borderStyles.borderStyle,
    borderTopColor: borderStyles.borderColor,
    borderBottomWidth: borderStyles.borderWidth,
    borderBottomStyle: borderStyles.borderStyle,
    borderBottomColor: borderStyles.borderColor,
    borderLeftWidth: borderStyles.borderWidth,
    borderLeftStyle: borderStyles.borderStyle,
    borderLeftColor: borderStyles.borderColor,
    borderRightWidth: borderStyles.borderWidth,
    borderRightStyle: borderStyles.borderStyle,
    borderRightColor: borderStyles.borderColor
  };
}