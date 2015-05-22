module.exports = function (config) {
    config.set({

        basePath: '../',

        files: [
            '../main/webapp/build/bower_components/jquery/jquery.js',
            '../main/webapp/build/bower_components/bootstrap/bootstrap.js',
            '../main/webapp/build/bower_components/angular/angular.js',
            '../main/webapp/build/bower_components/angular-animate/angular-animate.js',
            '../main/webapp/build/bower_components/angular-resource/angular-resource.js',
            '../main/webapp/build/bower_components/angular-ui-router/angular-ui-router.js',
            '../main/webapp/build/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            '../main/webapp/build/bower_components/ng-tags-input/ng-tags-input.min.js',
            '../main/webapp/build/bower_components/angular-mocks/angular-mocks.js',
            '../main/webapp/build/js/min/script.min.js',
            'javascript/unit/**/*.js'
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
	    'karma-junit-reporter'
        ],

        singleRun: true,
        reporters: ['coverage', 'dots', 'progress', 'html', 'junit'],

        preprocessors: {
            '../main/webapp/build/js/**/*.js': ['coverage'],
            'client/*': ['browserify']
        },
    
	junitReporter: {
	      outputFile: '../../target/site/report/XML/test-results.xml',
	},

        coverageReporter: {
            reporters: [
                {type: 'lcovonly', dir: '../../target/site/cobertura/'},
                {type : 'cobertura', dir: '../../target/site/cobertura/'},
                {type : 'html', dir: '../../target/site/report/'}
            ]
        },
	
	htmlReporter: {
            outputFile: '../../target/site/report/units.html'
        }

    });
};
