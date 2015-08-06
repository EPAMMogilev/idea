'use strict';

var FormPage = require('../ideaForm/form.po.js');
var HomePage = require('../home/home.po.js');
var DetailsPage = require('../ideaDetails/details.po.js');

describe('update idea page test', function() {

	var formPage = new FormPage();
	var homePage = new HomePage();
	var detailsPage = new DetailsPage();

	beforeEach(function() {
		homePage.getPage();
		homePage.ideaDetails(5);
		detailsPage.update();
	});

	it('should update idea', function() {
		formPage.updateIdea("New", "New", "New");
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/home');
		});
	});

});