var ConversionUtility= require('../promiseConversion-utility.js');

var HomePage = function() {};
HomePage.prototype = Object.create({}, {
	popularIdeas:   { get: function() { return element.all(by.repeater('idea in ideasCtrl.popular')); }},
	latestIdeas:    { get: function() { return element.all(by.repeater('idea in ideasCtrl.latest')); }},
	ideas:          { get: function() { return element.all(by.css('.idea-li')); }},

	loginButton:    { get: function() { return element(by.id('words')); }},
	addButton:      { get: function() { return element(by.id('ideaAddNew')); }},
	allButton:      { get: function() { return element(by.id('all')); }},
	tagButtons:     { get: function() { return element.all(by.repeater('tag in tagsCtrl.tagsTop')); }},
	likeButtons:    { get: function() { return element.all(by.css('.btn-thumbs')); }},
	myIdeasButton:  { get: function() { return element(by.id('myideas')); }},
	userNameButton:  { get: function() { return element(by.id('username')); }},
	searchField:    { get: function() { return element(by.model('query')); }},

	ratings:        { get: function() { return element.all(by.id('rating')); }},
	ideaLogos:      { get: function() { return element.all(by.id('imgIdeaLogo')); }},
	authors:        { get: function() { return element.all(by.id('author')); }},
	ideaTags:       { get: function() { return element.all(by.id('tag')); }},

	states:        { get: function() { return element.all(by.id('state')); }},
	titles:        { get: function() { return element.all(by.id('title')); }},
	descriptions:  { get: function() { return element.all(by.id('description')); }},

	login:      { value: function() { return this.loginButton.click(); }},
	addIdea:    { value: function() { return this.addButton.click(); }},
	getAll:     { value: function() { return this.allButton.click(); }},
	getByTag:   { value: function(id) { return this.tagButtons.get(id).click(); }},
	getRating:  { value: function(id) { return ConversionUtility.getIntValue(this.ratings.get(id)); }},
	getAuthor:  { value: function(id) { return this.authors.get(id).getText(); }},
	getIdeaTag: { value: function(id) { return this.ideaTags.get(id).getText(); }},
	like:       { value: function(id) { return this.likeButtons.get(id).click(); }},
	ideaDetails:{ value: function(id) { return this.ideaLogos.get(id).click(); }},

	getTagText: { value: function(id) { return this.tagButtons.get(id).getText(); }},

	getState:       { value: function(id) { return this.states.get(id).getInnerHtml(); }},
	getTitle:       { value: function(id) { return this.titles.get(id).getInnerHtml(); }},
	getDescription: { value: function(id) { return this.descriptions.get(id).getInnerHtml(); }},

	search:     { value: function(query) { this.searchField.sendKeys(query); }},

	getMyIdeas: { value: function() {
		this.userNameButton.click();
		return this.myIdeasButton.click();
	}},

	getPage:    { value: function() { browser.get(''); }}
});

module.exports = HomePage;
