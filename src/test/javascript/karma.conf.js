module.exports = function (config) {
    config.set({

        basePath: '../../../',

        files: [
            'src/main/webapp/build/bower_components/jquery/jquery.js',
            'src/main/webapp/build/bower_components/bootstrap/bootstrap.js',
            'src/main/webapp/build/bower_components/angular/angular.js',
            'src/main/webapp/build/bower_components/angular-animate/angular-animate.js',
            'src/main/webapp/build/bower_components/angular-resource/angular-resource.js',
            'src/main/webapp/build/bower_components/angular-ui-router/angular-ui-router.js',
            'src/main/webapp/build/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'src/main/webapp/build/bower_components/ng-tags-input/ng-tags-input.min.js',
            'src/main/webapp/build/bower_components/angular-mocks/angular-mocks.js',
            //'main/webapp/build/js/min/script.min.js',
	        'src/main/webapp/build/js/*.js',
	        'src/main/webapp/build/js/**/*.js',
            'src/test/javascript/unit/**/*.js',
            'src/main/webapp/build/templates/*.tpl.html'
        ],
	exclude: [
	    'src/main/webapp/build/js/min/*.js',
	],

        autoWatch: true,

        frameworks: ['jasmine'],

        browserNoActivityTimeout: 30000,

        browsers: ['Firefox'],

        plugins: [
            'karma-firefox-launcher',
            'karma-htmlfile-reporter',
            'karma-jasmine',
            'karma-coverage',
            'karma-junit-reporter',
			'karma-ng-html2js-preprocessor'
        ],

        singleRun: true,
        reporters: ['coverage', 'dots', 'progress', 'html', 'junit'],

        preprocessors: {
            'src/main/webapp/build/templates/*.tpl.html':['ng-html2js'],			
            'src/main/webapp/build/js/**/*.js': ['coverage'],
            'src/client/*': ['browserify']
        },
		
		ngHtml2JsPreprocessor: {
			// strip main/webapp/build/ from the file path
			stripPrefix: 'src/main/webapp/build/',
			moduleName: 'idea.templates'
		},
    
    junitReporter: {
        outputFile: 'target/site/report/XML/test-results.xml',
    },

        coverageReporter: {
            reporters: [
                {type: 'lcovonly', dir: 'target/site/cobertura/'},
                {type : 'cobertura', dir: 'target/site/cobertura/'},
                {type : 'html', dir: 'target/site/report/'}
            ]
        },
    
    htmlReporter: {
            outputFile: 'target/site/report/units.html'
        }

    });
};
