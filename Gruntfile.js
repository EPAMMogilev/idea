module.exports = function (grunt) {

    var userConfig = require( './build.config.js' );
    var json = grunt.file.readJSON('package.json');

    var protractorBrowserPort = grunt.option('p:port') || '8282';




    var taskConfig = {

        pkg:json,

        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */'
            },
            target: {
                files: {
                    '<%= resource_dir %>/js/min/script.min.js': ['<%= resource_dir %>/js/min/script.clean.js']
                }
            }
        },
        cssmin: {
            add_banner: {
                options: {
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                        '<%= grunt.template.today("yyyy-mm-dd") %> */'
                },
                files: {
                    '<%= resource_dir %>/css/min/style.min.css': ['<%= resource_dir %>/css/style.css']
                }
            }
        },

         index: {

              /**
               * During development, we don't want to have wait for compilation,
               * concatenation, minification, etc. So to avoid these steps, we simply
               * add all script files directly to the `<head>` of `index.html`. The
               * `src` property contains the list of included files.
               */
              build: {
                dir: '<%= build_dir %>',
                src: [
                  '<%= build_dir %>/**/*.js',
//                  '<%= html2js.common.dest %>',
//                  '<%= html2js.app.dest %>',
//                  '<%= vendor_files.css %>',
                  '<%= build_dir %>/assets/<%= pkg.name %>-<%= pkg.version %>.css'
                ]
              }
              },

        clean: {
            jsmin: ['<%= resource_dir %>/js/min/'],
            cssmin: ['<%= resource_dir %>/css/min/'],
            all: ['<%= build_dir %>']

        },

        copy: {

            build_appjs: {
            files: [
              {
                src: [ '<%= app_files.js %>' ],
                dest: '<%= build_dir %>/',
                cwd: '<%= resource_dir %>/',
                expand: true
              }
            ]
            },

            build_css: {
                  files: [
                    {
                      src: [ '<%= app_files.css %>' ],
                      dest: '<%= build_dir %>/',
                      cwd: '<%= resource_dir %>/',
                      expand: true
                    }
                  ]
            },

            build_img: {
                  files: [
                    {
                      src: [ '<%= app_files.img %>' ],
                      dest: '<%= build_dir %>/',
                      cwd: '<%= resource_dir %>/',
                      expand: true
                    }
                  ]
            }
        },

        removelogging: {
            dist: {
                files: {
                    '<%= resource_dir %>/js/min/script.clean.js': ['<%= app_files.jslong %>']
                }
            }
       },

     bower: {
            install: {
               options: {
                    targetDir: '<%= build_dir %>/bower_components'
               }
            }
         }
    };

    grunt.initConfig( grunt.util._.extend( taskConfig, userConfig ) );

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-ngdocs');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-ng-annotate');
    grunt.loadNpmTasks('grunt-processhtml');
    grunt.loadNpmTasks('grunt-remove-logging');
    grunt.loadNpmTasks('grunt-protractor-runner');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-bower-task');


    grunt.registerTask('copy1', ['copy:build_css']);
    grunt.registerTask('build', [ 'clean','removelogging', 'uglify',
    'clean:cssmin', 'cssmin', 'index:build', 'copy','bower']);
//    grunt.registerTask('p:test', ['clean:e2etests','processhtml:e2eTests', 'connect', 'protractor', 'processhtml:production']);
//    grunt.registerTask('k:test', ['processhtml:development', 'karma', 'copy:lcov']);

    /**
    * A utility function to get all app JavaScript sources.
    */
    function filterForJS ( files ) {
    return files.filter( function ( file ) {
      return file.match( /\.js$/ );
    });
    }

    /**
    * A utility function to get all app CSS sources.
    */
    function filterForCSS ( files ) {
    return files.filter( function ( file ) {
      return file.match( /\.css$/ );
    });
    }

    grunt.registerMultiTask( 'index', 'Process index.html template', function () {
    var dirRE = new RegExp( '^('+grunt.config('build_dir')+'|'+grunt.config('compile_dir')+')\/', 'g' );
    var jsFiles = filterForJS( this.filesSrc ).map( function ( file ) {
      return file.replace( dirRE, '' );
    });
    var cssFiles = filterForCSS( this.filesSrc ).map( function ( file ) {
      return file.replace( dirRE, '' );
    });

    grunt.file.copy(grunt.config('resource_dir') + '/index.html', this.data.dir + '/index', {
      process: function ( contents, path ) {
        return grunt.template.process( contents, {
          data: {
            scripts: jsFiles,
            styles: cssFiles,
            version: grunt.config( 'pkg.version' )
          }
        });
      }
    });
    });

};
