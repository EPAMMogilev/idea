var ConversionUtility = require('../promiseConversion-utility.js');

var DetailsPage = function() {comments = new CommentList()};

DetailsPage.prototype = Object.create({}, {
	updateButton: { get: function() { return element(by.id('btnUpdate')); }},

	title:   { get: function() { return element.all(by.id('tdData')).get(0); }},
	desc:    { get: function() { return element.all(by.id('tdData')).get(1); }},
	author:  { get: function() { return element.all(by.id('tdData')).get(2); }},
	rating:  { get: function() { return element(by.id('rating')); }},

	commentField:   { get: function() { return element(by.id('newComment')); }},
	addCommentButton:  { get: function() { return element(by.id('btnAddComment')); }},

	comments: { get: function() { return comments; }},

	getTitle:   { value: function() { return this.title.getText(); }},
	getDesc:    { value: function() { return this.desc.getText(); }},
	getAuthor:  { value: function() { return this.author.getText(); }},
	getRating:  { value: function() { return ConversionUtility.getIntValue(this.rating); }},

	likeButton:    { get: function() { return element(by.css('.btn-thumbs')); }},
	like:          { value: function() { return this.likeButton.click(); }},

	addComment:    { value: function(body) {
		this.commentField.sendKeys(body);
		this.addCommentButton.click();
	}},

	update: { value: function() { this.updateButton.click(); }}
});

var CommentList = function() {};

CommentList.prototype = Object.create({}, {

	commentAuthors:        { get: function() { return element.all(by.css('.userName')); }},
	commentBodies:         { get: function() { return element.all(by.id('commentBody')); }},
	commentRatings:        { get: function() { return element.all(by.id('commentRating')); }},

	getAuthor:  { value: function(id) { return this.commentAuthors.get(id).getText(); }},
	getBody:    { value: function(id) { return this.commentBodies.get(id).getText(); }},
	getRating:  { value: function(id) {
		var rating = this.commentRatings.get(id);
		return rating.getAttribute('class').then(function(elemClass) {
		   if(elemClass.indexOf('ng-hide') !== -1) {
			   return 0;
		   } else {
			   return ConversionUtility.getIntValue(rating.getText());
		   }
		});
	}},

	likeButtons:   { get: function() { return element.all(by.id('likeButton')); }},
	like:          { value: function(id) { return this.likeButtons.get(id).click(); }}
});

module.exports = DetailsPage;