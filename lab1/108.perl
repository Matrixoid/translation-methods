while (<>) {
	print if /\(\s*\w+[\s\w]*\)/;
}