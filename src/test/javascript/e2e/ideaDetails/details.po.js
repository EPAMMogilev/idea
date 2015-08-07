var DetailsPage = function() {};

DetailsPage.prototype = Object.create({}, {
	updateButton: { get: function() { return element(by.id('btnUpdate')); }},

	update: { value: function() { this.updateButton.click(); }}
});

module.exports = DetailsPage;