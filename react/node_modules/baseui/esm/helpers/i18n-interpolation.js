/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/
// Example:
// getInterpolatedString('Selected date is ${date}', 'Jan 1, 2020') => 'Selected date is Jan 1, 2020'
export default function getInterpolatedString(translation) {
  var interpolations = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
  return translation.replace(/\${(.*?)}/g, function (_, k) {
    return interpolations[k] === undefined ? '${' + k + '}' : interpolations[k];
  });
}