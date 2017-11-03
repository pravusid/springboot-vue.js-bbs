module.exports = {
  parserOptions: {
    parser: 'babel-eslint',
    ecmaVersion: 2017,
    sourceType: 'module',
  },
  extends: [
    'airbnb-base',
    'plugin:vue/recommended', // or 'plugin:vue/base'
  ],
  rules: {
    // override/add rules' settings here
    'vue/valid-v-if': 'error',
    "linebreak-style": 0,
  },
};
