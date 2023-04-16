/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/
import * as React from 'react';
export var LevelContext = /*#__PURE__*/React.createContext(0);
export var HeadingLevel = function HeadingLevel(_ref) {
  var children = _ref.children;
  return /*#__PURE__*/React.createElement(LevelContext.Consumer, null, function (level) {
    return /*#__PURE__*/React.createElement(LevelContext.Provider, {
      value: level + 1
    }, children);
  });
};
export default HeadingLevel;