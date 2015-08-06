var FormPage = function() {};

FormPage.prototype = Object.create({}, {
	tagField:   { get: function() { return element.all(by.id('searchLine')).get(0); }},
	titleField: { get: function() { return element.all(by.id('searchLine')).get(1); }},
	descField:  { get: function() { return element.all(by.id('searchLine')).get(2); }},
	addUpdateButton:  { get: function() { return element(by.id('btnInsertUpdate')); }},

	addIdea:    { value: function(tag, title, desc) {
		this.tagField.sendKeys(tag);
		this.titleField.sendKeys(title);
		this.descField.sendKeys(desc);
		this.addUpdateButton.click();
	}},

	updateIdea: { value: function(tag, title, desc) { this.addIdea(tag, title, desc); }}
});

module.exports = FormPage;