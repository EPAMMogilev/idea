'use strict';

var DetailsPage = require('../ideaDetails/details.po.js');
var HomePage = require('../home/home.po.js');

describe('details idea page test', function() {

	var detailsPage = new DetailsPage();
	var homePage = new HomePage();

	beforeEach(function() {
		homePage.getPage();
		homePage.ideaDetails(0);
	});

	it('should open idea update page', function() {
		detailsPage.update();
		expect(browser.getCurrentUrl()).toMatch("/ideaUpdate");
	});


});