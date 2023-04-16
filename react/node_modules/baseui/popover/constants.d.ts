export declare const PLACEMENT: {
    readonly auto: "auto";
    readonly topLeft: "topLeft";
    readonly top: "top";
    readonly topRight: "topRight";
    readonly rightTop: "rightTop";
    readonly right: "right";
    readonly rightBottom: "rightBottom";
    readonly bottomRight: "bottomRight";
    readonly bottom: "bottom";
    readonly bottomLeft: "bottomLeft";
    readonly leftBottom: "leftBottom";
    readonly left: "left";
    readonly leftTop: "leftTop";
};
export declare const TRIGGER_TYPE: {
    readonly click: "click";
    readonly hover: "hover";
};
export declare const STATE_CHANGE_TYPE: {
    readonly open: "open";
    readonly close: "close";
};
export declare const ACCESSIBILITY_TYPE: {
    readonly none: "none";
    readonly menu: "menu";
    readonly tooltip: "tooltip";
};
export declare const POPOVER_MARGIN = 8;
export declare const ARROW_SIZE = 6;
export declare const ANIMATE_OUT_TIME = 0;
export declare const ANIMATE_IN_TIME = 20;
/**
 * Since we use a 45-degree rotated div to render the arrow, the
 * width/height of this div is different than the arrow size itself
 *
 * The arrow size is essentially half the diagonal of the rotated div,
 * using pythagorean theorem:
 *   width^2 + height^2 = (arrow_size * 2)^2
 * In this case width = height so:
 *   2 * width^2 = (arrow_size * 2)^2
 * Simplifies to:
 *   width = √((arrow_size * 2)^2 / 2)
 */
export declare const ARROW_WIDTH: number;
