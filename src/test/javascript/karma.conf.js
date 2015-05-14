module.exports = function (config) {
    config.set({

        basePath: '../',

        files: [
            '../main/webapp/build/bower_components/jquery/jquery.js',
            '../main/webapp/build/bower_components/bootstrap/bootstrap.js',
            '../main/webapp/build/bower_components/angular/angular.js',
	        '../main/webapp/build/bower_components/angular-mocks/angular-mocks.js',
            '../main/webapp/build/bower_components/angular-animate/angular-animate.js',
            '../main/webapp/build/bower_components/angular-resource/angular-resource.js',
            '../main/webapp/build/bower_components/angular-ui-router/angular-ui-router.js',
            '../main/webapp/build/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            '../main/webapp/build/bower_components/ng-tags-input/ng-tags-input.min.js',
            '../main/webapp/build/bower_components/angular-mocks/angular-mocks.js',
            '../main/webapp/js/**/*.js',
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
            'karma-coverage'
        ],

        singleRun: true,
        reporters: [/*'progress', */'coverage', 'dots', 'progress', 'html'],

        preprocessors: {
            '../main/webapp/js/**/*.js': ['coverage'],
            'client/*': ['browserify']
        },

        coverageReporter: {
            reporters: [
                {type: 'lcovonly', dir: '../../target/site/cobertura/'},
                {type : 'cobertura', dir: '../../target/site/cobertura/'},
                {type : 'html', dir: '../../target/site/report/'}/*,
                {type : 'html', dir : '../../target/site/coverage/', file : 'coverage.txt'}*/
            ]
        },
	
	htmlReporter: {
            outputFile: '../../target/site/report/units.html'
        }

    });
};