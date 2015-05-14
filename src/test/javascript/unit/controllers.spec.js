describe('controllers unit testing', function(){

	var vmIdeas;

	beforeEach(module('app.controllers'));

	beforeEach(inject(function($controller) {
		vmIdeas = $controller('ideasCtrl');
	}));

//	Verify that the controllers can be instantiated
	it('should be instantiable', function () {
		expect(vmIdeas).toBeDefined();
		expect(vmIdeas.tag).toEqual('Все');
	});

});


