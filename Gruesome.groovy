import java.util.Random

class Gruesome {
  static String VERSION = '0.0.1'

  static Closure genInt  = { new Random().nextInt() }
  static Closure genBool = { new Random().nextBoolean() }
  static Closure genByte = { (Gruesome.genInt().abs() % 256) as int }
  static Closure genChar = { (Gruesome.genByte() % 128) as char }

  static Collection genArray(g) {
    Integer len = Gruesome.genInt().abs() % 100
    (0 .. len).collect { i -> g() }
  }

  static Closure genString = { Gruesome.genArray(Gruesome.genChar).join('') }

  static apply(Closure clos, Collection args) {
    def c = clos
    args.each { a -> c = c.curry(a) }

    c()
  }

  static forAll(Closure property, Collection generators) {
    Collection testCases = (0 .. 99).collect { i -> generators.collect({ g -> g() }) }
    Collection failures = testCases.findAll { testCase -> !apply(property, testCase) }

    if (failures.size > 0) {
      println '*** Failed!'
      println failures[0]
    }
    else {
      println '+++ OK, passed 100 tests.'
    }
  }
}
