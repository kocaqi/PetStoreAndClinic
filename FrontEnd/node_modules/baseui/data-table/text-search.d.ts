import React from 'react';
export declare function matchesQuery(text: string, query: string): boolean;
export declare function splitByQuery(text: string, query: string): string[];
export declare const HighlightCellText: React.FC<{
    text: string;
    query: string;
}>;
