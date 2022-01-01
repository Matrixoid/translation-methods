#include <vector>
#include <algorithm>

void vd() {
    return;
}

int mul5(int x, int y, bool z = true) {
    return x + x * 2 + 2 * x;
}

bool isTrue(bool val, int k = 3, int p = 1) {

    if(p * k == 5) {
        return true;
    } else {
        return false;
    }

}

int main() {

    int a = 5;
    bool b = true;
    float c = 5 / 2 * 3;
    bool d = isTrue(false);
    string str;
    if (b == d) {
        str = "true";
    } else {
        str = "false";
    }
    a += mul5(3);
    std::cout << str << std::endl;

}