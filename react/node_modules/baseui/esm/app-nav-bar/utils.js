/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/
export function defaultMapItemToNode(item) {
  if (process.env.NODE_ENV !== "production") {
    if (!item.label) {
      throw Error('There needs to be an unique item.label. You can implement a custom mapping with the mapItemToNode prop.');
    }
  }

  return item.label;
}

function defaultGetUniqueIdentifier(item) {
  if (process.env.NODE_ENV !== "production") {
    if (!item.label) {
      throw Error('There needs to be an unique item.label. You can implement a custom mapping with the getUniqueIdentifier argument to setItemActive.');
    }
  }

  return item.label;
}

export function mapItemsActive(items, predicate) {
  return items.map(function (current) {
    if (predicate(current)) {
      current.active = true;
    } else {
      current.active = false;
    }

    if (current.children) {
      current.children = mapItemsActive(current.children, predicate);

      if (current.children.some(function (child) {
        return child.active;
      })) {
        current.active = true;
      }
    }

    return current;
  });
}
export function setItemActive(items, item) {
  var getUniqueIdentifier = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : defaultGetUniqueIdentifier;
  return mapItemsActive(items, function (current) {
    return getUniqueIdentifier(current) === getUniqueIdentifier(item);
  });
}