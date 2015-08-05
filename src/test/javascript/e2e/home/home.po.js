var HomePage = function() {
	browser.get('http://evbymogsd0030.minsk.epam.com:7080/idea');
};

HomePage.prototype = Object.create({}, {
	popularIdeas: {get: function() { return element.all(by.repeater('idea in ideasCtrl.popular')); }},
	latestIdeas: {get: function() { return element.all(by.repeater('idea in ideasCtrl.latest')); }},
	allButton: {get: function() { return element(by.id('all')); }},
	tagButtons: {get: function() { return element.all(by.repeater('tag in tagsCtrl.tagsTop')); }},
	likeButtons: {get: function() {return element.all(by.css('.btn-thumbs')); }},
	ratings: {get: function() { return element.all(by.id('rating')); }},
	loginButton: {get: function() { return element(by.id('words')); }},


	getByTag: {value: function(id) {return this.tagButtons.get(id).click(); }},
	getAll: {value: function() { return this.allButton.click(); }},
	login: {value: function() { return this.loginButton.click(); }},
	like: {value: function(id) { return this.likeButtons.get(id).click(); }},
	getRating: {value: function(id) { return this.ratings.get(id).getText(); }}
});

module.exports = HomePage;