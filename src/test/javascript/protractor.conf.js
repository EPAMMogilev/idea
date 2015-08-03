var HtmlScreenshotReporter = require('protractor-jasmine2-screenshot-reporter');

exports.config = {
	allScriptsTimeout: 300000,
	
	rootElement: "[ng-app]",

	specs: ['e2e/*.js'],

	capabilities: {
		//'browserName': 'firefox',
		'browserName': process.env.BROWSER_NAME || 'firefox',
		'firefox_binary': process.env.FF_PORTABLE
	},

	//baseUrl: 'http://localhost:8282/',

	framework: 'jasmine2',
	
	onPrepare: function() {
		jasmine.getEnv().addReporter(new HtmlScreenshotReporter({
			dest: 'src/main/webapp/build/e2eresultshtml',
			filename: 'idea-e2e-report.html',
		}));
		
		var jasmineReporters = require('jasmine-reporters');
		jasmine.getEnv().addReporter(new jasmineReporters.JUnitXmlReporter({
			consolidateAll: true,
			filePrefix: 'xmloutput',
			savePath: 'src/main/webapp/build/e2eresultsxml'
		}));
	},

	jasmineNodeOpts : {
		// onComplete will be called just before the driver quits.
		onComplete : null,
		// If true, display spec names.
		isVerbose : true,
		// If true, print colors to the terminal.
		showColors : true,
		// If true, include stack traces in failures.
		includeStackTrace : true,
		// Default time to wait in ms before a test fails.
		defaultTimeoutInterval : 300000
	}
};