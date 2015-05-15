describe('appIdea', function() {

	browser.get('/');

	it('should filter results ideas list', function() {
		// Find the all and get second (and only) button on the page and click it
		element.all(by.css("a[type='button']")).get(1).click();
		// Verify that there are 2 places
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(2);

		// Find the all and get third (and only) button on the page and click it
		element.all(by.css("a[type='button']")).get(2).click();
		// Verify that there are 1 places
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(1);

		// Find the all and get fourth (and only) button on the page and click it
		element.all(by.css("a[type='button']")).get(3).click();
		// Verify that there are 0 places
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(0);

		// Find the all and get first (and only) button on the page and click it
		element.all(by.css("a[type='button']")).get(0).click();
		// Verify that there are 3 places
		expect(element.all(by.repeater('idea in ideasCtrl.ideas')).count()).toEqual(3);	
	});

});