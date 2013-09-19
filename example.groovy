#!/usr/bin/env groovy

Closure isEven = { i -> i % 2 == 0 }

static Closure genEven() {
  Integer i = Gruesome.genInt()

  if (i % 2 as int != 0) {
    i + 1
  } else { i }
}

String reverse(String s) { String s ->
  ((s.length() - 1) .. 0).collect { i -> s.charAt(i) }.join('')
}

Closure reversible = { String s -> reverse(reverse(s)) == s }

/* Are all integers even? */
Gruesome.forAll(isEven, [Gruesome.genInt])

/* Are all even integers even? */
Gruesome.forAll(isEven, [genEven])

/* Are all strings reversible? */
Gruesome.forAll(reversible, [Gruesome.genString])
