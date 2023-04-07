/*
Copyright (c) Uber Technologies, Inc.

This source code is licensed under the MIT license found in the
LICENSE file in the root directory of this source tree.
*/

/**
  r == range - date range can be selected
  d == disabled - disabled date
  h == highlighted - currently highlighted date, the highlight is triggered on hover or focus
  mO == hovered (mouse-over) - currently hovered date
  s == selected - selected date, in a range both start and end date are marked as `selected`
  rS == range-selected - when start and end dates of a range are set
  sD == start-date - selected start date of the range
  eD == end-date - selected end date of the range
  pS == pseudo-selected - any date between two selected dates in a range
  rH == range-highlighed - when only a single date of a range selected and the second date is highlighted but not yet selected
  pH == pseudo-highlighted -  any date between a selected date in a range and the currently highlighted date (case when only one date selected in a range case)
  rR == range-on-right - the range-highlighed case with the highlighed date is after the selected one
  rL == range-on-left - the range-highlighed case with the highlighed date is before the selected one
  sM == start-of-month - the first day of the month
  eM == end-of-month - the last day of the month
  oM == outside-month - date outside of currently displayed month (when peekNextMonth is set to true)
 */
//  r  d  h  s  rS sD eD pS rH pH rR rL sM eM oM
//  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1
export default function getDayStateCode(props) {
  var _props$$range = props.$range,
      $range = _props$$range === void 0 ? false : _props$$range,
      _props$$disabled = props.$disabled,
      $disabled = _props$$disabled === void 0 ? false : _props$$disabled,
      _props$$isHighlighted = props.$isHighlighted,
      $isHighlighted = _props$$isHighlighted === void 0 ? false : _props$$isHighlighted,
      _props$$isHovered = props.$isHovered,
      $isHovered = _props$$isHovered === void 0 ? false : _props$$isHovered,
      _props$$selected = props.$selected,
      $selected = _props$$selected === void 0 ? false : _props$$selected,
      _props$$hasRangeSelec = props.$hasRangeSelected,
      $hasRangeSelected = _props$$hasRangeSelec === void 0 ? false : _props$$hasRangeSelec,
      _props$$startDate = props.$startDate,
      $startDate = _props$$startDate === void 0 ? false : _props$$startDate,
      _props$$endDate = props.$endDate,
      $endDate = _props$$endDate === void 0 ? false : _props$$endDate,
      _props$$pseudoSelecte = props.$pseudoSelected,
      $pseudoSelected = _props$$pseudoSelecte === void 0 ? false : _props$$pseudoSelecte,
      _props$$hasRangeHighl = props.$hasRangeHighlighted,
      $hasRangeHighlighted = _props$$hasRangeHighl === void 0 ? false : _props$$hasRangeHighl,
      _props$$pseudoHighlig = props.$pseudoHighlighted,
      $pseudoHighlighted = _props$$pseudoHighlig === void 0 ? false : _props$$pseudoHighlig,
      _props$$hasRangeOnRig = props.$hasRangeOnRight,
      $hasRangeOnRight = _props$$hasRangeOnRig === void 0 ? false : _props$$hasRangeOnRig,
      _props$$startOfMonth = props.$startOfMonth,
      $startOfMonth = _props$$startOfMonth === void 0 ? false : _props$$startOfMonth,
      _props$$endOfMonth = props.$endOfMonth,
      $endOfMonth = _props$$endOfMonth === void 0 ? false : _props$$endOfMonth,
      _props$$outsideMonth = props.$outsideMonth,
      $outsideMonth = _props$$outsideMonth === void 0 ? false : _props$$outsideMonth;
  return "".concat(+$range).concat(+$disabled).concat(+($isHighlighted || $isHovered)).concat(+$selected).concat(+$hasRangeSelected).concat(+$startDate).concat(+$endDate).concat(+$pseudoSelected).concat(+$hasRangeHighlighted).concat(+$pseudoHighlighted).concat(+($hasRangeHighlighted && !$pseudoHighlighted && $hasRangeOnRight)).concat(+($hasRangeHighlighted && !$pseudoHighlighted && !$hasRangeOnRight)).concat(+$startOfMonth).concat(+$endOfMonth).concat(+$outsideMonth);
}