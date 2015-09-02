var ConversionUtility= require('../promiseConversion-utility.js');

var DetailsPage = function() {};

DetailsPage.prototype = Object.create({}, {
	updateButton: { get: function() { return element(by.id('btnUpdate')); }},

	title:   { get: function() { return element.all(by.id('tdData')).get(0).getText(); }},
	desc:    { get: function() { return element.all(by.id('tdData')).get(1).getText(); }},
	author:  { get: function() { return element.all(by.id('tdData')).get(2).getText(); }},
	rating:  { get: function() { return ConversionUtility.getIntValue(element.all(by.id('divRating'))); }},

	update: { value: function() { this.updateButton.click(); }}
});

module.exports = DetailsPage;