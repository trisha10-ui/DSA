#include <vector>
#include <string>
#include <algorithm>

using namespace std;

struct Node {
    int max_len;
    int prefix_len;
    int suffix_len;
    char left_char;
    char right_char;
};

class SegmentTree {
private:
    int n;
    vector<Node> tree;

    Node merge(const Node& left, const Node& right, int left_len, int right_len) {
        Node res;
        res.left_char = left.left_char;
        res.right_char = right.right_char;

        res.prefix_len = left.prefix_len;
        if (left.prefix_len == left_len && left.right_char == right.left_char) {
            res.prefix_len += right.prefix_len;
        }

        res.suffix_len = right.suffix_len;
        if (right.suffix_len == right_len && left.right_char == right.left_char) {
            res.suffix_len += left.suffix_len;
        }

        res.max_len = max(left.max_len, right.max_len);
        if (left.right_char == right.left_char) {
            res.max_len = max(res.max_len, left.suffix_len + right.prefix_len);
        }

        return res;
    }

    void build(const string& s, int node, int start, int end) {
        if (start == end) {
            tree[node] = {1, 1, 1, s[start], s[start]};
            return;
        }
        int mid = start + (end - start) / 2;
        build(s, 2 * node, start, mid);
        build(s, 2 * node + 1, mid + 1, end);
        tree[node] = merge(tree[2 * node], tree[2 * node + 1], mid - start + 1, end - mid);
    }

    void update(int node, int start, int end, int idx, char ch) {
        if (start == end) {
            tree[node] = {1, 1, 1, ch, ch};
            return;
        }
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(2 * node, start, mid, idx, ch);
        } else {
            update(2 * node + 1, mid + 1, end, idx, ch);
        }
        tree[node] = merge(tree[2 * node], tree[2 * node + 1], mid - start + 1, end - mid);
    }

public:
    SegmentTree(const string& s) {
        n = s.length();
        tree.resize(4 * n);
        build(s, 1, 0, n - 1);
    }

    void updateChar(int idx, char ch) {
        update(1, 0, n - 1, idx, ch);
    }

    int getMax() const {
        return tree[1].max_len;
    }
};

class Solution {
public:
    vector<int> longestRepeating(string s, string queryCharacters, vector<int>& queryIndices) {
        SegmentTree st(s);
        int k = queryIndices.size();
        vector<int> result(k);

        for (int i = 0; i < k; ++i) {
            st.updateChar(queryIndices[i], queryCharacters[i]);
            result[i] = st.getMax();
        }

        return result;
    }
};

// Synced seamlessly with LeetHub Pro
// Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
// Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna