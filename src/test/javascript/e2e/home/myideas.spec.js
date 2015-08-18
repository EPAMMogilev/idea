'use strict';

var HomePage = require('../home/home.po.js');
var LoginPage = require('../login/login.po.js');

describe('my ideas page test', function() {

	var homePage = new HomePage();
	var loginPage = new LoginPage();

	beforeAll(function() {
		loginPage.getPage();
		loginPage.login('first@idea.com', '1234');
	});

	beforeEach(function() {
		homePage.getPage();
		homePage.getMyIdeas();
	});

	it('should open my ideas page', function() {
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('%')[0].split('#')[1]).toContain('/myideas');
		});
		homePage.authors.count().then(function(count) {
			for(var i = 0; i < count; i++) {
				expect(homePage.getAuthor(i)).toBe('FirstUser');
			}
		});
	});

});