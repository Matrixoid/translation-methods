while (<>) {
	s/\(([^)]*)\)/\(\)/g;
	print;
}