var AccessDeniedPage = function() {};

AccessDeniedPage.prototype = Object.create({}, {
	backButton:{ get: function() { return element(by.css('.btn')); }},

	goToHome: { value: function() { this.backButton.click(); }},
});

module.exports = AccessDeniedPage;