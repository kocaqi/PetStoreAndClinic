/* @flow */
module.exports = {
  root: true,
  parser: '@babel/eslint-parser',
  env: {
    node: true,
    jest: true,
    es6: true,
  },
  extends: ['eslint:recommended'],
  plugins: ['flowtype'],
  rules: {
    'flowtype/define-flow-type': 1,
    'flowtype/require-valid-file-annotation': ['error', 'always'],

    indent: ['error', 2],
    'linebreak-style': ['error', 'unix'],
    quotes: ['error', 'single', 'avoid-escape'],
    semi: ['error', 'always'],
    'no-var': ['error'],
    'brace-style': ['error'],
    'array-bracket-spacing': ['error', 'never'],
    'block-spacing': ['error', 'always'],
    'no-spaced-func': ['error'],
    'no-whitespace-before-property': ['error'],
    'space-before-blocks': ['error', 'always'],
    'keyword-spacing': ['error'],
  },
};
