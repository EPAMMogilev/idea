'use strict';

exports.getIntValue = function(elem) {
  return elem.getText().then(function(str) {
   return parseInt(str);
  });
}