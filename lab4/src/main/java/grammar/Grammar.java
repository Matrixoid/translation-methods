package grammar;

import java.util.*;

public class Grammar {
    private final String EPS = "";
    private final String END = "END";

    private String startNonTerm;
    private List<NonTermRule> nonTermRules = new ArrayList<>();
    private List<TermRule> termRules = new ArrayList<>();
    private Map<String, Set<String>> firstSet = new HashMap<>();
    private Map<String, Set<String>> followSet = new HashMap<>();

    public Grammar(String startNonTerm) {
        this.startNonTerm = startNonTerm;
    }

    public List<NonTermRule> getNonTermRules() {
        return nonTermRules;
    }

    public List<TermRule> getTermRules() {
        return termRules;
    }

    public String getStartNonTerm() {
        return startNonTerm;
    }

    public Set<String> getFollowSet(String nonTermName) {
        return followSet.get(nonTermName);
    }

    public void addNonTermRule(NonTermRule nonTermRule) {
        nonTermRules.add(nonTermRule);
    }

    public void addTermRule(TermRule termRule) {
        termRules.add(termRule);
    }

    private void initSet(Map<String, Set<String>> set) {
        if (set.size() > 0) return;
        for (NonTermRule nonTermRule : nonTermRules) {
            set.put(nonTermRule.getName(), new HashSet<>());
        }
    }

    public void buildFirstAndFollowSets() {
        buildFirstSet();
        buildFollowSet();
    }

    public Set<String> getFirstSetByRightPart(List<RuleToken> rightPart) {
        Set<String> result = new HashSet<>();

        boolean isEps = true;
        for (RuleToken ruleToken : rightPart) {
            if (ruleToken instanceof Code) continue;
            if (ruleToken instanceof Term) {
                result.add(ruleToken.getName());
            } else {
                result.addAll(firstSet.get(ruleToken.getName()));
            }
            isEps = false;
            break;
        }
        if (isEps) {
            result.add(EPS);
        }

        return result;
    }

    private void buildFirstSet() {
        initSet(firstSet);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (NonTermRule nonTermRule : nonTermRules) {
                for (List<RuleToken> rightPart : nonTermRule.getRightParts()) {
                    changed |= firstSet.get(nonTermRule.getName()).addAll(getFirstSetByRightPart(rightPart));
                }
            }
        }
    }

    private void buildFollowSet() {
        initSet(followSet);
        followSet.get(startNonTerm).add(END);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (NonTermRule nonTermRule : nonTermRules) {
                for (List<RuleToken> rightPart : nonTermRule.getRightParts()) {
                    for (int i = 0; i < rightPart.size(); i++) {
                        RuleToken A = rightPart.get(i);
                        if (A instanceof Term || A instanceof Code) {
                            continue;
                        }

                        Set<String> addToFollow = getFirstSetByRightPart(rightPart.subList(i + 1, rightPart.size()));
                        if (addToFollow.remove(EPS)) {
                            addToFollow.addAll(followSet.get(nonTermRule.getName()));
                        }
                        changed |= followSet.get(A.getName()).addAll(addToFollow);
                    }
                }
            }
        }
    }

    public boolean isLL1() {
        for (NonTermRule nonTermRule : nonTermRules) {
            HashSet<String> setF = new HashSet<>();
            for (List<RuleToken> rightPart : nonTermRule.getRightParts()) {
                boolean flag = true;
                for (RuleToken token : rightPart) {
                    Set<String> set;
                    flag = true;
                    if (token instanceof Term) {
                        set = Set.of(((Term) token).getName());
                    } else
                    if (token instanceof NonTerm) {
                        NonTerm beta = (NonTerm) token;
                        set = firstSet.get(beta.getName());
                        if (set.contains(EPS)) {
                            set.remove(EPS);
                            flag = false;
                        }
                    } else {continue;}
                    if (!set.isEmpty()) {
                        if (setF.isEmpty()) {
                            setF.addAll(set);
                        } else {
                            int size = setF.size();
                            setF.addAll(set);
                            if (size != setF.size() - set.size()) {
                                return false;
                            }
                        }
                        if (flag) {
                            break;
                        }
                    }
                }
                if (!flag) {
                    setF.add(EPS);
                }
            }

        }
        return true;
    }

    private boolean hasLeftRecursion() {
        for (NonTermRule nonTermRule : nonTermRules) {
            for (List<RuleToken> rightPart : nonTermRule.getRightParts()) {
                for (RuleToken token : rightPart) {
                    if (token instanceof Term) break;
                    if (token instanceof NonTerm) {
                        if (token.getName().equals(nonTermRule.getName())) return true;
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasRight() {
        for (NonTermRule nonTermRule : nonTermRules) {
            Set<String> firstTokens = new HashSet<>();
            for (List<RuleToken> rightPart : nonTermRule.getRightParts()) {
                for (RuleToken token : rightPart) {
                    if (token instanceof Code) continue;

                    if (firstTokens.contains(token.getName())) {
                        return true;
                    }
                    firstTokens.add(token.getName());
                    break;
                }
            }
        }
        return false;
    }
}
