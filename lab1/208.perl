while (<>) {
    s/\b(0*)(\d+)0/$2/g;
    print;
}