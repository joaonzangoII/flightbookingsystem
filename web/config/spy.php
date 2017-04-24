<?php

// dd( app_path('lib'));
return array(

	/*
	 |--------------------------------------------------------------------------
	 | Directory where generated files will be written
	 |--------------------------------------------------------------------------
	 |
	 | NOTE: For security reasons, setting this option to any path within your
	 | public_path() directory will be a really bad idea.
	 |
	 | Default: app_path('database/schema')
	 |
	 */

	'output' => app_path('database/schema'),

	/*
	 |--------------------------------------------------------------------------
	 | Base command to run schemaSpy.jar on your system
	 |--------------------------------------------------------------------------
	 |
	 | No parameters here, instead use the 'parameters' array below!
	 |
	 | Default: java -jar schemaSpy.jar
	 |
	 */

	'command' => 'java -jar '  . app_path('lib')  . '/schemaSpy_5.0.0.jar',

	/*
	|--------------------------------------------------------------------------
	| Extra parameters to pass to the command
	|--------------------------------------------------------------------------
	|
	| Database connection settings will be read form Laravel's database config
	| file but they can be overridden here.
	|
	| Full list of possible parameters: http://schemaspy.sourceforge.net/
	|
	*/

	'parameters' => [
		'-t'  => 'mysql',
		'-dp' => app_path('lib') . '/mysql-connector-java-5.1.41-bin.jar', // download from http://dev.mysql.com/downloads/connector/j/
		'-hq' => null,
	],
);
