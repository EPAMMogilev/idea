var FormPage = function() {};

FormPage.prototype = Object.create({}, {
	tagField:   { get: function() { return element.all(by.id('searchLine')).get(0); }},
	titleField: { get: function() { return element.all(by.id('searchLine')).get(1); }},
	descField:  { get: function() { return element.all(by.id('searchLine')).get(2); }},
	addUpdateButton:  { get: function() { return element(by.id('btnInsertUpdate')); }},

	titleError:   { get: function() { return element(by.id('ideaRequiredMessage')); }},
	descError:    { get: function() { return element(by.id('descRequiredMessage')); }},

	addIdea:    { value: function(tag, title, desc) {
		this.fillTag(tag);
		this.fillTitle(title);
		this.fillDesc(desc);
		this.addUpdateButton.click();
	}},

	fillTitle:    { value: function(title) {
		this.titleField.sendKeys(title);
	}},

	fillDesc:    { value: function(desc) {
		this.descField.sendKeys(desc);
	}},

	fillTag:    { value: function(tag) {
		this.tagField.sendKeys(tag);
	}},

	clearTitle:    { value: function(title) {
		this.titleField.clear();
	}},

	clearDesc:    { value: function(desc) {
		this.descField.clear();
	}},

	updateIdea: { value: function(tag, title, desc) { this.addIdea(tag, title, desc); }}
});

module.exports = FormPage;