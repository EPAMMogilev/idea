var ConversionUtility= require('../promiseConversion-utility.js');

var DetailsPage = function() {};

DetailsPage.prototype = Object.create({}, {
	updateButton: { get: function() { return element(by.id('btnUpdate')); }},

	title:   { get: function() { return element.all(by.id('tdData')).get(0); }},
	desc:    { get: function() { return element.all(by.id('tdData')).get(1); }},
	author:  { get: function() { return element.all(by.id('tdData')).get(2); }},
	rating:  { get: function() { return element(by.id('rating')); }},

	getTitle:   { value: function() { return this.title.getText(); }},
	getDesc:    { value: function() { return this.desc.getText(); }},
	getAuthor:  { value: function() { return this.author.getText(); }},
	getRating:  { value: function() { return ConversionUtility.getIntValue(this.rating); }},

	likeButton:    { get: function() { return element(by.css('.btn-thumbs')); }},
	like:          { value: function() { return this.likeButton.click(); }},

	update: { value: function() { this.updateButton.click(); }}
});

module.exports = DetailsPage;