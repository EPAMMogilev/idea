var HomePage = function() {
	browser.get('http://evbymogsd0030.minsk.epam.com:7080/idea');
};

HomePage.prototype = Object.create({}, {
	popularIdeas: {get: function() { return element.all(by.repeater('idea in ideasCtrl.popular')); }},
	latestIdeas: {get: function() { return element.all(by.repeater('idea in ideasCtrl.latest')); }},
	tagButtons: {get: function() { return element.all(by.repeater('tag in tagsCtrl.tagsTop')); }},
	allButton: {get: function() { return element(by.id('all')); }},
	loginButton: {get: function() { return element(by.id('words')); }},

	getByTag: {value: function(id) {return this.tagButtons.get(id).click(); }},
	getAll: {value: function() { return this.allButton.click(); }},
	login: {value: function() { return this.loginButton.click(); }}
});

module.exports = HomePage;