(ns hello_clojure
  (:require [clojure.java.javadoc :refer [javadoc]]
            [clojure.repl :refer [source apropos dir pst doc find-doc]]
            [clojure.string :as string]
            [clojure.test :refer [is are]]))

;; Start with loading this file
;; Ctrl+Alt+C Enter

;; Then Alt+Enter this:

"Hello World"

;; That's a concise Hello World.
;; And note that there are no parens. 😀

;; This guide will try to give you a basic
;; understanding of the Clojure language. Basic in
;; the sense that it is not extensive. Basic in the
;; sense that it is foundational. Building from first
;; principles in order to make the Clojure journey
;; you have ahead easier to comprehend.

;; With the foundations in place you'll have a good 
;; chance of having the right gut feeling for how to
;; code something, to formulating your questions,
;; googling for information, make sense of code you
;; stumble across, and so on.

;; There will be links here and there, ctrl/cmd-click
;; those to open them in a browser. Here's the first
;; such link; 
;; https://clojure.org/guides/learn/syntax
;; There you can read more about the concepts
;; mentioned in this guide.

;; The way to use the guide is to read about the
;; concepts and evaluate the examples. Please feel
;; encouraged to edit the examples, and add new code
;; and evaluate that. Evaluate this to warm up:

(str "Evaluate"
     " and "
     "experiment")

(comment
  ;; = EXPRESSIONS =
  ;; In Clojure everything is an expression.
  ;; (There are no statements.) Unless there is
  ;; en error when evaluating the expressions there
  ;; is always a return value (which is sometimes `nil`).

  ;; An important aspect of this is that the result
  ;; of an expression is always the last form/expression
  ;; evaluated. E.g. if you have a function defined
  ;; like so:

  (defn last-eval-wins []
    (println 'side-effect-1)
    1
    (println 'side-effect-2)
    2)

  ;; (Just, evaluate it. This defines a function named
  ;; `last-eval-wins`, taking no arguments, with four
  ;; expressions in its function body. We'll return to
  ;; defining functions.)
  ;; Calling the function (Just evaluate it.)

  (last-eval-wins)

  ;; will cause all four expressions in the function
  ;; body to be evaluated. The result of the call will
  ;; be the last expression that was evaluated.

  ;; In the output window you will also see the
  ;; `println` calls happening. They are also
  ;; expressions, evaluating `nil`.

  (println 'prints-this-evaluates-to-nil)

  ;; Expressions are composed from literals (evaluating
  ;; to themselves) and/or calls to either
  ;; * special forms
  ;; * macros
  ;; * functions 
  ;; Calls are written as lists with the called thing
  ;; as the first element.

  (str 1 2 3)

  ;; Calls the function `str` with the arguments
  ;; 1, 2, and 3. ”Hello World” above is a literal
  ;; string (thus it evaluates to itself).
  ;; More about literals coming up next!
  )

(comment
  ;; = LITERALS =
  ;; Literals evaluate to themselves.
  ;; (Remember your friends:
  ;;   Alt+Enter and Ctrl+Enter)

  ;; Numeric types
  18        ; integer
  -1.8      ; floating point
  0.18e2    ; exponent
  18.0M     ; big decimal
  108/20    ; ratio
  18N       ; big integer
  0x12      ; hex
  022       ; octal
  2r10010   ; base 2

  ;; Character types
  "hello"         ; string
  \e              ; character
  #"[0-9]+"       ; regular expression

  ;; Symbols and idents
  map             ; symbol
  +               ; symbol - most punctuation allowed
  clojure.core/+  ; namespaced symbol
  nil             ; null value
  true false      ; booleans
  :alpha          ; keyword
  :release/alpha  ; keyword with namespace

  ;; == STRINGS ==
  ;; Somewhere in between the atomic literals and
  ;; the collections we have strings. They are sometimes
  ;; treated as sequences (a cool abstraction we'll
  ;; talk more about).
  ;; Strings are enclosed by double quotes. 

  "A string can be
   multi-line, but will contain any leading spaces."
  "Write strings
like this, if leading spaces are no-no."

  ;; (The single quote is used for something else.
  ;; You'll see to what a bit later.)
  )

(comment
  ;; = COLLECTIONS =
  ;; Clojure has literal syntax for four collection types
  ;; They evaluate to themselves.

  '(1 2 3)     ; list (a quoted list, more about this below)
  [1 2 3]      ; vector
  #{1 2 3}     ; set
  {:a 1 :b 2}  ; map

  ;; They compose

  {:foo [1 2]
   :bar #{1 2}}

  ;; = FUNCTIONS =
  ;; So far you have been able to evaluate all examples.
  ;; It's because we quoted that list.
  ;; Actually lists look like so

  (1 2 3)

  ;; But if you evaluate that, you'll get an error:
  ;; => class java.lang.Long cannot be cast to class clojure.lang.IFn
  ;; (Of course, the linter already warned you.)
  ;; This is because the Clojure will try to call
  ;; `1` as a function. When evaluating unquoted lists
  ;; the first element in the list is regarded as being
  ;; in ”function position”. A Clojure program is data. 
  ;; In fancier words, Clojure is homoiconic:
  ;; https://wiki.c2.com/?HomoiconicLanguages
  ;; This gives great macro power, more about that below

  ;; Here are some lists with proper functions at
  ;; position 1:

  (str 1 2 3 4 5 :foo)
  (< 1 2 3 4 5)
  (*)
  (= "1"
     (str "1")
     (str \1))
  (println "From Clojure with ♥️")
  (reverse [5 4 3 2 1])

  ;; Everything after the first position is
  ;; handed to the function as arguments

  ;; Note: We refer to literals, symbols and literal
  ;; collections, collectively as forms, sometimes,
  ;; sexprs: https://en.wikipedia.org/wiki/S-expression

  ;; You define new functions and bind them to names
  ;; in the current namespace using the macro `defn`.
  ;; It's a very flexible macro. Here's a simple use:

  (defn add2
    [arg]
    (+ arg 2))

  ;; It defines the function `add2` taking one argument.
  ;; The function body calls the core functions `+`
  ;; with the arguments `arg` and 2.
  ;; Evaluating the form will define it and you'll see:
  ;; => #'hello-clojure/add2
  ;; That's a var ”holding” the value of the function
  ;; You can now reference the var using the symbol
  ;; `add2`. Putting it in the function position of a
  ;; list with 3 in the first argument position and 
  ;; evaluating the list gives us back what?

  (add2 3)

  ;; Clojure has an extensive core library of functions
  ;; and macros. See: https://clojuredocs.org for a community
  ;; driven Clojure core (and more) search engine.
  )

(comment
  ;; = SPECIAL FORMS =
  ;; The core library is composed from the functions and macros
  ;; in the library itself. Bootstrapping the library is
  ;; a few (15-ish) built-in primitive forms,
  ;; aka ”special forms”.

  ;; You have met one of these special forms already:

  (quote (1 2 3))

  ;; The doc hover of the symbol `quote` tells you that
  ;; it is a special form.

  ;; Wondering where you met this special form before?
  ;; We used the shorthand syntax for it then:

  '(1 2 3)

  ;; Convince yourself they are the same with the `=` function:

  (= (quote (1 2 3))
     '(1 2 3))

  ;; Clojure has value semantics. Any data structures
  ;; that evaluate to the same data are equal,
  ;; no matter how deep or big the structures are.

  (= [1 [1 #{1 {:a 1 :b '(:foo bar)}}]]
     [1 [1 #{1 {:a (- 3 2) :b (quote (:foo bar))}}]])

  ;; ... but that was a detour, back to special forms.
  ;; Official docs:
  ;; https://clojure.org/reference/special_forms#_other_special_forms

  ;; A very important special form is `fn` (which is
  ;; actually four special forms, but anyway).
  ;; Without this form we can't define new functions.
  ;; The following form evaluates to a function which 
  ;; adds 2 to its argument.

  (fn [arg] (+ arg 2))

  ;; Calling the function with the argument 3:

  ((fn [arg] (+ arg 2)) 3)

  ;; Another special form is `def`. It defines things,
  ;; giving them namespaced names.

  (def foo :foo)

  ;; ”Defining a thing” means that a var is created,
  ;; holding the value, and that a symbol is bound
  ;; to the var. Evaluating the symbol, picks up the
  ;; value from the var it is bound to

  foo

  ;; The var can be accessed using the `var` special
  ;; form.

  (var foo)

  ;; You will most often see the var-quote shorthand

  #'foo

  ;; With these two special forms we can define functions

  (def add2-2 (fn [arg] (+ arg 2)))
  (add2-2 3)

  ;; This is what the macro `defn` does.
  ;; We can use the function `macroexpand` to see this:

  (macroexpand '(defn add2
                  [arg]
                  (+ arg 2)))

  ;; Yet another super duper important special form:

  (if 'test
    'value-if-true
    'value-if-false)

  ;; Rumour has it that all conditional constructs (macros)
  ;; are built using `if`. Try to imagine a programming language
  ;; without conditionals!

  ;; We'll return to `if` and conditionals. Let's wrap
  ;; the special forms section up with just noting that
  ;; together with _how_ Clojure reads and evaluates code,
  ;; the special forms make up the Clojure language
  ;; itself. The next level och building blocks are
  ;; macros. But let's investigate this with how code
  ;; is read first...
  )

(comment
  ;; = THE READER =
  ;; https://clojure.org/reference/reader
  ;; The Clojure Reader is responsible for reading text,
  ;; making data from it, which is what the compiler gets.
  ;; The Reader is where literals, symbols, strings, lists,
  ;; vectors, maps, and sets are picked apart and
  ;; re-assembled, figuring out what is a function,
  ;; a macro or special form.

  ;; In doing this whitespace plays a key role and there
  ;; are also some extra syntax rules are in play.

  ;; == WHITESPACE ==
  ;; Most things you would think counts as whitespace
  ;; is whitespace, and then there is also that Clojure,
  ;; being a LISP, does not need commas to separate
  ;; list items, since whitespace is enough. However,
  ;; Commas can be used for this, since:
  ;; commas are whitespace!

  (= '(1 2 3)
     '(1,2,3)
     '(1, 2, 3)
     '(1,,,,2,,,,3))

  ;; (There are no operators in Clojure, `=` is a
  ;; function. It will check for equality of all
  ;; arguments it is passed.)

  ;; == LINE COMMENTS ==
  ;; The Reader skips reading everything on a line from
  ;; a semicolon. This is unstructured comments in
  ;; that if you start a form

  (range 1 ; 10)
  ;; and then place a line comment so that the closing
  ;; bracket of that form gets commented out, the
  ;; structure breaks.
         )
  ;;     ^ Healing the structure.        

  ;; If you remove the semicolon on the opening form
  ;; above, make sure to also remove this closing paren.           

  ;; Since everything on the line is ignored, you can
  ;; add as many semicolons as you want.
  ;;;;;;;;;; (skipped by the Reader)
  ;; It's common to use two semicolons to start a full
  ;; line comment. 

  ;; == EXTRA SYNTAX ===
  ;; We've already seen the single quote

  'something

  ;; Which is, as we have seen, transformed to

  (quote something)

  ;; There are some more quoting, and even splicing
  ;; symbols, which we won't cover in this guide.

  ;; === Deref ===
  ;; Clojure also has reference types, we'll discuss
  ;; (briefly) the most common one, `atom`, later.

  (def an-atom (atom [1 2 3]))
  (type an-atom)

  ;; To access value from a reference:

  (deref an-atom)
  (type (deref an-atom))

  ;; This is so common that there is shorthand syntax

  @an-atom
  (= (deref an-atom)
     @an-atom)

  ;; It's a common mistake to forget to deref

  (first an-atom)
  (first @an-atom)

  ;; === THE DISPATCHER (HASH SIGN) ===
  ;; Regular expressions have literal syntax, they are
  ;; written like strings, but with a hash sign in front

  #"reg(?:ular )?exp(?:ression)?"

  ;; Regexps are handled by the host platform, so they
  ;; are Java regexps in this tutorial.

  (re-seq *1 "regexp regular expression")

  ;; *1 is a special symbol for a variable holding the
  ;; value of the last evaluation result.

  ;; That hash sign shows up now and then, for instance

  #(+ % 2)

  ;; Which is special syntax for ”function literals”, a
  ;; way to specify a function.
  ;; The example above is equivalent to this anonymous
  ;; function.

  (fn [arg] (+ arg 2))

  ;; Nesting function literals is forbidden activity

  ;(#(+ % (#(- % 2) 3)))

  ;; (thankfully)

  ;; The hash sign has a special role. It is aka
  ;; Dispatch. Depending on what character is following
  ;; it, different cool things happen.
  ;; In addition to sets, regexps and function literals
  ;; we have seen var-quotes in this guide

  #'add2

  ;; There is a very useful hash-dispatcher which
  ;; is used to make the Reader ignore the next form

  #_(println "The reader will not send this function call
to the compiler") "This is not ignored"

  ;; To test this select the ignore marker together with
  ;; the function call and the string, then use Ctrl+Enter,
  ;; to make Calva send it all to the Reader, which will
  ;; read it, ignore the function call, and only evaluate
  ;; the string.

  ;; Since #_ ignores the next form it is a structural
  ;; comment mechanism, often used to temporarily disable
  ;; some code or some data

  (str "a" "b" #_(str 1 2 3 [4 5 6]) "c")

  ;; Ignore markers stack

  (str "a" #_#_"b" (str 1 2 3 [4 5 6]) "c")

  ;; Note that the Reader _will_ read the ignored form.
  ;; If there are syntactic errors in there, the
  ;; Reader will get sad, complain, and stop Reading.
  ;; Select from the marker up to and including the string
  ;; here and press Ctrl+Enter

  ;#_(#(+ % (#(- % 2) 3))) "foo"
  
  ;; Two more common #-variants you will see, and use,
  ;; are namespaced map keyword shorthand syntax and
  ;; tagged literals, aka, data readers. Let's start
  ;; with the former:

  (= #:foo {:bar 'bar
            :baz 'baz}
     {:foo/bar 'bar
      :foo/baz 'baz})

  ;; Unrelated to the #: There is another shorthand for
  ;; specifying namespaced keywords. Double colon
  ;; keywords get namespaced with the current namespace

  ::foo
  (= ::foo :hello-clojure/foo)

  ;; Tagged literals, then. It's a way to invoke functions
  ;; bound to the tags on the form following it.
  ;; https://clojure.org/reference/reader#tagged_literals
  ;; They are also referred to as data readers. You can
  ;; define your own. Here let it suffice with mentioning
  ;; the two build in ones.

  ;; #inst will convert the string it tags to an instance

  #inst "2018-03-28T10:48:00.000"
  (type *1)

  ;; #uuid will make an UUID of the string it tags

  #uuid "0000000-0000-0000-0000-000000000016"
  (java.util.UUID/fromString "0000000-0000-0000-0000-000000000016")

  ;; You now know how to read (in the sense of you
  ;; being a Clojure Reader) most Clojure code.
  ;; That said, let's skip going into the syntax
  ;; sugar and special forms for making host platform
  ;; interop extra nice.
  ;; https://clojure.org/reference/java_interop
  ;; Just a sneak peek:

  (.before #inst "2018-03-28T10:48:00.000"
           #inst "2021-02-17T00:27:00.000")

  ;; This invokes the method `before` on the date
  ;; object for year 2018 giving it the date from
  ;; year 2021 as argument.
  )

(comment
  ;; = MACROS =
  ;; Clojure has powerful data transformation
  ;; capabilities. We'll touch on that a bit later.
  ;; Here we want to highlight that this power can
  ;; be wielded for extending the language. 
  ;; Since Clojure code is structured and code is
  ;; data, Clojure can be used to produce Clojure
  ;; code from Clojure code. It is similar to the
  ;; preprocessor facilitates that some languages
  ;; offer, like C's `#pragma`, but it is much more
  ;; convenient and powerful. A lot of you will learn
  ;; to love and recognize as Clojure is actually
  ;; created with Clojure, as macros.

  ;; This guide is mostly concerned with letting you
  ;; know that macros are a thing, to help you to
  ;; quickly realize when you are using a macro rather 
  ;; than a function. So we will not go into the
  ;; subject of how to create macros.

  ;; == `when` ==
  ;; Let's just briefly examine the macro`when`.
  ;; This macro helps with writing more readable code.
  ;; How? Let's say you want to conditionally evaluate
  ;; something. Above you learnt that there is
  ;; a special form named `if` that can be used for
  ;; this. Like so:

  (if 'this-is-true
    'evaluate-this
    'else-evaluate-this)

  ;; Now say you don't have something to evaluate
  ;; in the else case. `if` allows you to write this

  (if 'this-is-true
    'evaluate-this)
  ;; Which is fine, but you will have to scan the
  ;; code a bit extra to see that there is no else
  ;; branch. To address this, you could write:

  (if 'this-is-true
    'evaluate-this
    nil)

  ;; But that is a bit silly, what if there was a
  ;; way to tell the human reading the code that
  ;; there is no else? There is!

  (when 'this-is-true
    'evaluate-this)

  ;; Let's look at how `when` is defined, you can
  ;; ctrl/cmd-click `when` to navigate to where
  ;; it is defined in Clojure core.
  ;; You can also use the function `macroexpand`

  (macroexpand '(when 'this-is-true
                  'evaluate-this))

  ;; You'll notice that `when` wraps the body in
  ;; a `(do ...)`, which is a special form that lets
  ;; you evaluate several expressions, returning the
  ;; results of the last one.
  ;; https://clojuredocs.org/clojure.core/do
  ;; `do` is handy when you want to have some side-
  ;; effect going in addition to evaluating something.
  ;; In development this often happens when you 
  ;; want to `println` something before result of the
  ;; expression is evaluated.

  (do (println "The quick brown fox jumps over the lazy dog")
      (+ 2 2))

  ;; The `when` macro let's you take advantage of that
  ;; there is only one branch, so you can do this

  (when 'this-is-true
    (println "The quick brown fox jumps over the lazy dog")
    (+ 2 2))

  ;; Without `when` you would write:

  (if 'this-is-true
    (do
      (println "The quick brown fox jumps over the lazy dog")
      (+ 2 2)))

  ;; Here `when` saves us both the extra scanning for
  ;; the else-branch and the use of `do`.

  ;; As far as macros go, `when` is about as simple as
  ;; they get. From two built-in special forms,
  ;; `if` and `do`, it composes a form that helps us
  ;; write easy to write and easy to read code. 

  ;; == `let` ==
  ;; A less simple core library macro is `let`. It
  ;; is a form that lets you bind values to variables
  ;; that will be used in the body of the form.

  (let [x 1
        y 2]
    (str x y))

  ;; The bindings are provided as the first ”argument”,
  ;; in a vector. This is a pattern that is used by
  ;; other macros that let you define bindings.
  ;; It is similar to the lexical scope of other
  ;; programming lannguages (even if this rather is
  ;; structural). Sibling and parent forms do not
  ;; ”see” these bindings.

  (do
    (def x :namespace-x)
    (println "`x` in `do` _before_ `let`: " x)
    (let [x :let-x]
      (println "`x` from `let`: " x))
    (println "`x` in `do`, _after_ `let`: " x))

  ;; The `def` special form defines things ”globally”

  (println "`x` _outside_ `do`: " x)

  ;; == `for` ==
  ;; The `for` macro really demonstrates how Clojure
  ;; can be extended using Clojure. You might think
  ;; it provides looping like, but in Clojure there
  ;; are no for loops. Instead `for` is about list
  ;; comprehensions (if you have Python experience,
  ;; yes, that kind of list comprehensions).
  ;; Here's how to produce the cartesian product of two
  ;; vectors, `x` and `y`:

  (for [x [1 2 3]
        y [1 2 3 4]]
    [x y])

  ;; `for` lets you filter the results

  (for [x [1 2 3]
        y [1 2 3 4]
        :when (not= x y)]
    [x y])

  ;; You can bind variable names in the comprehension

  (for [x [1 2 3]
        y [1 2 3 4]
        :let [d' (- x y)
              d (Math/abs d')]]
    d)

  ;; Filters and bindings can be used together.
  ;; Use both `:let` and `:when` to make this
  ;; comprehension return a list of all `[x y]` where
  ;; their sum is odd. The functions `+` and `odd?`
  ;; are your friends here.

  (for [x [1 2 3]
        y [1 2 3 4]]
    [x y])

  ;; See https://www.youtube.com/watch?v=5lvV9ICwaMo for
  ;; a great primer on Clojure list comprehensions
  ;; See https://clojuredocs.org/clojure.core/for for
  ;; example usages and tips.

  ;; Note that even though `let` and `for` look like
  ;; functions, they are not. The compiler would not
  ;; like it if you are passing undefined symbols to a
  ;; function. This is legal code:

  (let [abc 1]
    2)

  ;; This isn't.

  (str [abc 1]
       1)

  ;; (Notice that the clj-kondo linter is marking the
  ;; first with a warning, and the second as an error)
  ;; Macros extend the Clojure compiler.
  ;; https://clojure.org/reference/macros

  ;; == Threading macros ==
  ;; Macros can totally rearrange your code. The
  ;; built-in ”threading” macros do this. Sometimes
  ;; when the nesting of function(-ish) calls get
  ;; deep it can get a bit hard to read and to keep
  ;; track of all the parens 

  (Math/abs
   (apply -
          (:d (zipmap
               [:a :b :c :d]
               (partition 2 [1 1 2 3 5 8 13 21])))))

  ;; You read Clojure from the innermost expression
  ;; and out, which gets easier with time, but an
  ;; experienced Clojure coder would still find it
  ;; easier to read this

  (->> [1 1 2 3 5 8 13 21]
       (partition 2)
       (zipmap [:a :b :c :d])
       :d
       (apply -)
       (Math/abs))

  ;; Let's read this together. The thread-last macro,
  ;; `->>` is used, it takes its first argument and
  ;; places it (threads it) as the last argument to
  ;; following function. The first such step in
  ;; isolation:

  (->> [1 1 2 3 5 8 13 21]
       (partition 2))

  ;; The first argument/element passed to `->>` is
  ;; `[1 1 2 3 5 8 13 21]`
  ;; This is inserted as the last element of the
  ;; function call `(partition 2)`, yielding:

  (partition 2 [1 1 2 3 5 8 13 21])

  ;; This partitions the list into lists of
  ;; 2 elements => `((1 1) (2 3) (5 8) (13 21))`
  ;; This new list is then inserted (threaded)
  ;; as the last argument to the next function,
  ;; yielding:

  (zipmap [:a :b :c :d] '((1 1) (2 3) (5 8) (13 21)))

  ;; Which ”zips” together a Clojure map using
  ;; the first list as keys and the second list
  ;; as values
  ;; => `{:a (1 1), :b (2 3), :c (5 8), :d (13 21)}`
  ;; This map is then threaded as the last argument
  ;; to the function `:d`

  (:d '{:a (1 1), :b (2 3), :c (5 8), :d (13 21)})

  ;; (In clojure keywords are functions that look
  ;;  themselves up in the map handed to them.)
  ;; => `(13 21)`
  ;; You know the drill by now, this is threaded

  (apply - '(13 21))

  ;; Which applies the `-` function over the list
  ;; => `-8`
  ;; Then this is threaded to `Math/abs`

  (Math/abs -8)
  ;; 🎉

  ;; (In many Clojure capable editors, including
  ;; Calva, there are commands for ”unwinding”
  ;; a thread, and for converting a nested
  ;; expressions into a thread. Search for ”thread”
  ;; among the commands.)
  ;; https://github.com/clojure-emacs/clj-refactor.el/wiki/cljr-unwind-all

  ;; There is also a thread-first macro
  ;; `->` https://clojuredocs.org/clojure.core/-%3E
  ;; Sometimes you neither want to thread first
  ;; of last. There is a macro for this too.
  ;; `as->` lets you bind a variable name to the
  ;; threaded thing and place it wherever you
  ;; fancy in each function call.

  (as-> 15 $
    (range 1 $ 3)
    (interpose ":" $))

  ;; https://clojuredocs.org/clojure.core/as-%3E

  ;; Other core threading macros are:
  ;; `cond->`, `cond->>`, `some->`, and `some->>`
  ;; https://clojuredocs.org/clojure.core/cond-%3E

  ;; Please feel encouraged to copy the examples
  ;; from ClojureDocs here and play with them.
  ;; Here's one:

  (cond-> 1        ; we start with 1
    true inc       ; the condition is true so (inc 1) => 2
    false (* 42)   ; the condition is false so the operation is skipped
    (= 2 2) (* 3)) ; (= 2 2) is true so (* 2 3) => 6 

  ;; See ”Threading with Style” by Stuart Sierra
  ;; for idiomatic use of the threading facilities.
  ;; https://stuartsierra.com/2018/07/06/threading-with-style
  )

;; With special forms, the special syntax of the Reader,
;; and macros, the foundations of what is the Clojure
;; language you use are laid. You can of course extend
;; the language further with libraries including macros
;; or create your own. However the core language, with
;; its macros is very expressive. Taking data oriented
;; approaches is often enough. Even to prefer, rather
;; than creating more macros.

;; On to flow control!

(comment
  ;; = Flow Control, Conditionals, Branching =
  ;; Clojure is richer than most languages in what it
  ;; offers us to let our programs flow the way we want
  ;; them to. Almost all the core library features for
  ;; this are implemented using the primitive (special
  ;; form) `if`. This is still the staple for us as
  ;; Clojure coders. It takes three forms as its
  ;; arguments:
  ;; 1. A condition to evaluate
  ;; 2. What to evaluate if the condition evaluates
  ;;    to something true (truthy)
  ;; 3. The form to evaluate if the condition does not
  ;;    evaluate to something truthy (the ”else” branch)
  ;; Roll this dice, some ten-twenty times, checking if
  ;; it is a six:

  (if (= 6 (inc (rand-int 6)))
    "One time out of six you get a six"
    "Five times out of six you get something else")

  ;; Since there are no statements in Clojure `if` is
  ;; the equivalent to the ternary `if` expression you
  ;; find in C and many other languages:
  ;;   test ? true-expression : false-expression
  ;; Pseudo code for our dice:
  ;;   int(rand() * 6) + 1 == 6 ?
  ;;     "One time out of six you get a six" :
  ;;     "Five times out of six you get something else";

  ;; == The Search for Truth ==
  ;; Again, in Clojure we use expressions evaluating to
  ;; values. When examined for branching all values
  ;; are either truthy or falsy. In fact, almost all
  ;; values are truthy

  (if true :truthy :falsy)
  (if :foo :truthy :falsy)
  (if '() :truthy :falsy)
  (if 0 :truthy :falsy)
  (if "" :truthy :falsy)

  ;; The only falsy values are `false` and `nil`

  (if false :truthy :falsy)
  (if nil :truthy :falsy)
  (when false :truthy)

  ;; About that last one: `when` evaluates to `nil`
  ;; when the condition is falsy. Since `nil` is 
  ;; falsy the above `when` expression would be
  ;; making the ”else” branch of an `if` to be
  ;; evaluated

  (if (when false :truthy) :true :falsy)

  ;; (Super extra bad code, but anyway)
  ;; When only boolean truth or falsehood can cut
  ;; it for you, there is the `true?` function

  (true? true)
  (true? 0)
  (true? '())
  (true? nil)
  (true? false)

  ;; Thus

  (if (true? 0) :true :false)

  ;; == `when` ==
  ;; As mentioned before, `when` is a one-branch
  ;; `if`, only for the truthy branch, which is
  ;; wrapped in a `do` for you. Try this and then
  ;; try it replacing the `when` with an `if`:

  (when :truthy
    (println "That sounds true to me")
    :truthy-for-you)

  ;; If the `when` condition is not truthy,
  ;; `nil` will be returned.

  (when nil :true-enough?)

  ;; == `cond` ==
  ;; Since deeply nested if/else structures can be
  ;; hard to write, read, and maintain, Clojure core
  ;; offers several more constructs for flow control,
  ;; one very common such is the `cond` macro. It
  ;; takes pairs of condition/result forms, tests
  ;; each condition, if it is true, then the result
  ;; form is evaluated and ”returned”, short-circuiting
  ;; so that no more condition is tested.

  (let [dice-roll (inc (rand-int 6))]
    (cond
      (= 6 dice-roll)  "Six is as high as it gets"
      (odd? dice-roll) (str "An odd roll " dice-roll " is")
      :else            (str "Not six, nor odd, instead: " dice-roll)))

  ;; The `:else` is just the keyword `:else` which
  ;; evaluates to itself and is truthy. It is the
  ;; conventional way to give your cond forms a
  ;; default value. Without a default clause, the
  ;; form would evaluate to `nil` for anything not-six
  ;; not-odd. Try it by placing two ignore markers
  ;; (`#_ #_`) in front of the `:else` keyword.

  ;; Gotta love ClojureDocs
  ;; https://clojuredocs.org/clojure.core/cond
  ;; Paste examples from there here and play around:

  ;; See also links to `cond->` info above

  ;; == `case` ==
  ;; A bit similar to `switch/case` constructs in
  ;; other languages, Clojure core has the `case`
  ;; macro which takes a test expression, followed by
  ;; zero or more clauses (pairs) of test constant/expr,
  ;; followed by an optional expr. (However, the body
  ;; after the test expression may not be empty.)

  (let [test-str "foo bar"]
    (case test-str
      "foo bar" (str "That's very " :foo-bar)
      "baz"     :baz
      (count    test-str)))

  ;; The trailing expression, if any, is ”returned” as
  ;; the default value.

  (let [test-str "foo bar"]
    (case test-str
      #_#_"foo bar" (str "That's very " :foo-bar)
      "baz"     :baz
      (count    test-str)))

  ;; If no clause matches and there is no default,
  ;; a run time error happens

  (let [test-str "foo bar"]
    (case test-str
      #_#_"foo bar" (str "That's very " :foo-bar)
      "baz"     :baz
      #_(count    test-str)))

  ;; WATCH OUT! A test constant must be a compile
  ;; time literal, and the compiler won't  help you
  ;; find bugs like this:

  (let [test-int 2
        two 2]
    (case test-int
      1     :one
      two   (str "That's not a literal 2")
      (str test-int ": Probably not expected")))

  ;; https://clojuredocs.org/clojure.core/case
  ;; Paste some `case` examples here and experiment

  ;; The Functional Design in Clojure podcast has a
  ;; fantastic episode about branching
  ;; https://clojuredesign.club/episode/089-branching-out/

  ;; == Less branching is good, right? ==
  ;; The core library is rich with functions that
  ;; helps you avoid writing branching code. Instead
  ;; you provide the condition as a predicate.
  ;; An often used predicate function is `filter`

  (filter even? [0 1 2 3 4 5 6 7 8 9 10 11 12])

  ;; and its ”sibling” `remove`

  (remove odd? [0 1 2 3 4 5 6 7 8 9 10 11 12])

  ;; Filtering sequences of values is a common task
  ;; and your programming time can instead be used
  ;; to decide _how_ it should be filtered, by writing
  ;; the predicate. Sometimes you don't even need to
  ;; do that, Clojure core is rich with predicates

  (zero? 0)
  (even? 0)
  (neg? 0)
  (pos? 0)
  (nat-int? 0)
  (empty? "")
  (empty? [])
  (empty? (take 0 [1 2 3]))
  (integer? -2/1)
  (indexed? [1 2 3])
  (indexed? '(1 2 3))

  ;; What's a predicate? For the purpose of this guide
  ;; A predicate is a function testing things for
  ;; truthiness. It is convention that these functions
  ;; end with `?`. Many take only one argument.

  ;; A handy predicate is `some?` which tests for
  ;; "somethingness”, if it is not `nil` it is
  ;; something

  (some? nil)
  (some? false)
  (some? '())

  ;; You can use it to test for if something is `nil`
  ;; by wrapping it in a call to the `not` function

  (not (some? nil))
  (not (some? false))

  ;; You get the urge to define a function named `nil?`,
  ;; right? You don't have to

  (nil? nil)
  (nil? false)

  ;; Clojure core also contains predicates that take
  ;; a predicate plus a collection to apply it on.
  ;; Such as `every?`

  (every? nat-int? [0 1 2])
  (every? nat-int? [-1 0 1 2])

  ;; Check the docs for `nat-int? and come up
  ;; with some more lists to test, like

  (every? nat-int? [0 1 2N]) ; 2N is not fixed precision
  (doc nat-int?)

  ;; This pattern with functions that take functions
  ;; as argument is common in Clojure. It spans beyond
  ;; predicates. Functions that take functions as
  ;; arguments are referred to as ”higher order
  ;; functions”.
  ;; https://en.wikipedia.org/wiki/Higher-order_function
  )

(comment
  ;; = Functions =
  ;; Before diving into higher order functions, let's
  ;; look at functions.Functions are first class
  ;; Clojure citizens and the main building blocks for
  ;; solving your business problems. 
  
  ;; We have seen a few ways you can create functions.
  ;; Here's an anonymous function that returns the
  ;; integer given to it, unless it is divisible by
  ;; 15, in which case it returns "fizz buzz".
  ;; (Not the full Fizz Buzz problem by any means.)

  (fn [n]
    (if (zero? (mod n 15))
      "fizz buzz"
      n))

  ;; Let's define it (bind it to a symbol we can use)

  (def fizz-buzz-1 (fn [n]
                     (if (zero? (mod n 15))
                       "fizz buzz"
                       n)))
  (fizz-buzz-1 2)
  (fizz-buzz-1 15)

  ;; There's a macro that lets us define and create
  ;; the function in one call

  (defn fizz-buzz-2 [n]
    (if (zero? (mod n 15))
      "fizz buzz"
      n))
  
  (fizz-buzz-2 4)

  ;; `defn` lets us provide documentation for the
  ;; function

  (defn fizz-buzz-3
    "Says 'fizz buzz' if `n` is divisible by 15,
     otherwise says `n`"
    [n]
    (if (zero? (mod n 15))
      "fizz buzz"
      n))
  
  (doc fizz-buzz-3) ; (or hover `fizz-buzz-3`)

  ;; It is easy to place the doc string wrong,
  ;; especially since it is common to write the `defn`
  ;; form like we did with `fizz-buzz-2` above.

  (defn fizz-buzz-4
    [n]
    "Says 'fizz buzz' if `n` is divisible by 15,
     otherwise says `n`"
    (if (zero? (mod n 15))
      "fizz buzz"
      n))

  ;; This specifies a fully valid function body, so
  ;; Clojure won't complain about it. But:

  (doc fizz-buzz-4)

  ;; clj-kondo's default configuration will help you
  ;; spot these errors. However, it can't help with
  ;; this:

  (defn only-the-last-eval-returns [x]
    [1 x]
    [2 x])

  (only-the-last-eval-returns "foo")

  ;; It is easy enough to spot like this and also to
  ;; wonder why you would ever write a function that
  ;; way. Yet you probably will do this mistake,
  ;; especially if you ever write some Hiccup, which
  ;; is a super nice way of writing HTML with Clojure
  ;; data structures. It's used by the popular Reagent
  ;; library
  ;; https://purelyfunctional.tv/guide/reagent/#hiccup
  ;; When you do the mistake and finish your hour-long
  ;; bug hunt, you will hear this guide whisper
  ;;   ”Called it!”

  ;; The argument binding vector of `fn` (and
  ;; therefore `defn`) binds each argument in order
  ;; to a name.

  (defn coords->str [x y]
    (str "x: " x ", y: " y))

  ;; == Variadic Functions ==
  ;; You can define functions that take an arbitrary
  ;; number of arguments by placing a `&` in front
  ;; of the last argument name. That binds the name
  ;; to a sequence that contains all the remaining
  ;; arguments.

  (defn lead+members [lead & members]
    {:lead lead
     :members members})

  (lead+members "Dave Mustain"
                "Marty Friedman"
                "Nick Menza"
                "David Ellefson")

  ;; == Multi-arity ==
  ;; Clojure supports function signatures based on
  ;; the number of arguments. The `defn` macro lets
  ;; you define each arity as a separate list. This
  ;; is often used to provide default values

  (defn hello
    ([] (hello "World"))
    ([s] (str "Hello " s "!")))

  (hello)
  (hello "Clojure Friend")

  ;; Or to create an ”identity” value for a function,
  ;; (A starting value that the rest of the operation
  ;; uses.) Say you want to add two x-y coordinates

  (defn add-coords-1 [coord-1 coord-2]
    {:x (+ (:x coord-1)
           (:x coord-2))
     :y (+ (:y coord-1)
           (:y coord-2))})

  (add-coords-1 {:x -2 :y 10}
                {:x 4 :y 6})

  ;; What if the requirements were that if the
  ;; function is called with ne argument it should
  ;; add it to the origin? (See what I did there?
  ;; The identity value is where the function
  ;; should start, so start from the origin. 😎)
  ;; We can see that `add-coords-1` fails here

  (add-coords-1 {:x -2 :y 10})

  ;; we need to add a one-arity

  (defn add-coords-2
    ([coord]
     (add-coords-2 {:x 0
                    :y 0}
                   coord))
    ([coord-1 coord-2]
     {:x (+ (:x coord-1)
            (:x coord-2))
      :y (+ (:y coord-1)
            (:y coord-2))}))

  (add-coords-2 {:x -2 :y 10})

  ;; Now if called with no arguments it should
  ;; return the origin, because if you do not add
  ;; any coordinate you stay at the start.
  ;; Write a function `add-coords-3` that returns
  ;; the origin when called like this 

  (add-coords-3)

  ;; It should still handle to be called like this

  (add-coords-3 {:x 3 :y 4})
  (add-coords-3 {:x 2 :y 4}
                {:x -4 :y -4})

  ;; It has to do with making the function compose
  ;; with other functions. E.g. the `apply` function
  ;; which is a higher order function that ”applies”
  ;; a function over a sequence. Right now we can
  ;; apply our `add-coords-2` function like this

  (apply add-coords-2 [{:x 1 :y 1} {:x 4 :y 4}])

  ;; And like this

  (apply add-coords-2 [{:x 1 :y 1}])

  ;; But not like this

  (apply add-coords-2 [])

  ;; But the `add-coords-3` function you created can

  (apply add-coords-3 [])

  ;; It will not handle an arbitrary long sequence
  ;; of coords, though. For that we would need one
  ;; more arity like so

  (defn add-coords-4
    ;; add zero-arity from your `add-coords-3` here
    ;; add one-arity from your `add-coords-3` here
    ([coord-1 coord-2]
     {:x (+ (:x coord-1)
            (:x coord-2))
      :y (+ (:y coord-1)
            (:y coord-2))})
    ([coord-1 coord-2 & more-coords]
     ;; Implement this arity when you have learnt
     ;; about the higher order function `reduce` 
     ))

  (apply add-coords-4 [{:x 1 :y 1}
                       {:x 1 :y 1}
                       {:x 1 :y 1}
                       {:x -6 :y -6}])

  ;; Listen to Eric Normand explain in more detail
  ;; why the identity of a function is important:
  ;; https://lispcast.com/what-is-a-functions-identity/

  ;; == Closures ==
  ;; When you create functions on the fly, lambdas,
  ;; if you like, you use either the `fn` special
  ;; form directly, or by proxy with the `#()` syntax.
  ;; This creates a closure, like it does in JavaScript
  ;; and other languages. That is, these function can
  ;; access snapshots of variables with the values they
  ;; had when the function was created

  (defn named-coords-factory [name]
    (fn [x y] {:name name
               :coords {:x x
                        :y y}}))

  (def bob-coords-fn (named-coords-factory "Bob"))
  (def fred-coords-fn (named-coords-factory "Fred"))

  (bob-coords-fn 0 0)
  (fred-coords-fn 5 5)
  (bob-coords-fn 7 7)

  ;; Closures are handy to create low-arity functions
  ;; inside let binding boxes for the function body
  ;; to use:

  (defn whisper-or-yell-or-ask [command sentence]
    (let [whisper (fn []
                    (str (string/lower-case sentence) command))
          yell (fn []
                 (str (string/upper-case sentence) command))
          ask (fn []
                (str sentence "?"))
          default (fn []
                    (str sentence command " ¯\\_(ツ)_/¯"))]
      (case command
        "" (whisper)
        "!" (yell)
        "?" (ask)
        (default))))

  ;; All functions created in the let binding box
  ;; ”close in” the `command` and the `sentence` so
  ;; the `case` can be kept terse and readable.

  (whisper-or-yell-or-ask "" "How wOnDerFuLLY NIce To seE")
  (whisper-or-yell-or-ask "!" "Hello tHERE")
  (whisper-or-yell-or-ask "?" "How are you doing")
  (whisper-or-yell-or-ask ":" "Oh well")

  ;; == The Attributes Map ==
  ;; The `defn` macro lets you add attributes to the
  ;; function in the form of a map. This gets added
  ;; as meta-data (some little more on that later)
  ;; to the var holding the function. The map goes
  ;; after the function name, and after any docs,
  ;; and before the arguments vector (or any arities)

  (defn i-have-attributes
    {:doc "Docs can be added like this too"
     :foo "Any attributes you fancy"}
    []
    "Good for you")

  (doc i-have-attributes)
  (meta #'i-have-attributes)

  ;; One handy attribute you can add is a test
  ;; function. Test runners will pick this up

  (defn fizz-buzz-5
    "That limited fizz-buzz function again"
    {:test (fn []
             (is (= "fizz-buzz" (fizz-buzz-5 15)))
             (is (= 3 (fizz-buzz-5 3))))}
    [n]
    (if (pos? (mod n 15))
      "fizz-buzz"
      n))

  (clojure.test/test-var #'fizz-buzz-5)
  ;; Oops! You'll need to fix the bugs. 😀

  ;; In fact maybe it is time you prepare for
  ;; your next Clojure job interview by implementing
  ;; a more complete Fizz Buzz?
  ;; https://en.wikipedia.org/wiki/Fizz_buzz

  (defn fizz-buzz
    "My Fizz Buzz solution"
    {:test (fn []
             (are [arg expected] (= expected (fizz-buzz arg))
               1  1
               3  "Fizz"
               4  4
               5  "Buzz"
               7  7
               15 "Fizz Buzz"
               20 "Buzz"))}
    [n])

  (clojure.test/test-var #'fizz-buzz)
  (map fizz-buzz (range 1 40))

  ;; The meta-data that has special meaning to
  ;; the compiler and various Clojure core
  ;; facilities is listed here:
  ;; https://clojure.org/reference/special_forms

  ;; Now, on to higher order functions!
  )

(comment
  ;; = Higher order functions =
  ;; A big contribution to what makes Clojure such a
  ;; powerful language is that functions are
  ;; ”first-class”
  ;; https://en.wikipedia.org/wiki/First-class_function
  ;; They can be values in collections (also keys
  ;; in maps) and can be passed as arguments to other
  ;; functions, and ”returned ”as results from
  ;; evaluations. You might be familiar with the
  ;; concept from languages like JavaScript.

  ;; Let's look at some higher order functions in
  ;; Clojure core. `some` calls the function on the
  ;; elements of its collection, one-by-one, and
  ;; returns the first truthy result, and will return
  ;; `nil` if the list is exhausted before some element
  ;; results in something truthy.

  (some even? [1 1 2 3 5 8 13 21])

  ;; Not to be confused with `some?`, which is not
  ;; a higher order function.

  (some some? [nil false])
  (some some? [nil nil])

  ;; A common idiom in Clojure is to look for things
  ;; in collection using a `set` as the predicate.
  ;; Yes, sets are functions. Used as functions they
  ;; will look up the argument given to them in
  ;; themselves.

  (#{"foo" "bar"} "bar")

  ;; Thus

  (some #{"foo"} ["foo" "bar" "baz"])
  (some #{"fubar"} ["foo" "bar" "baz"])

  ;; `apply` takes a function and a collection and
  ;; ”applies” the function on the collection. Say you
  ;; have a collection of numbers and want to add them.
  ;; This won't work:

  (+ [1 1 2 3 5 8 13 21])

  ;; `apply` to the rescue

  (apply + [1 1 2 3 5 8 13 21])

  ;; Concatenate the numbers as a string:

  (apply str [1 1 2 3 5 8 13 21])

  ;; Contrast with

  (str [1 1 2 3 5 8 13 21])
  
  ;; We've also seen `filter` and `remove` above, two
  ;; very commonly used higher order functions. They
  ;; play in the same league as `map`, and `reduce`.
  ;; Read on. 😎
  )

(comment
  ;; = `map` and `reduce` =
  ;; Among the higher order functions you might have
  ;; used in other languages with first class
  ;; functions are `map` and `reduce`. They are worth
  ;; studying and practicing in much detail, here's
  ;; a super nice teaser:
  ;; https://purelyfunctional.tv/courses/3-functional-tools/

  ;; Let's also check them out briefly here.
  ;; `map` calls a function on the elements of one or
  ;; more collection from start to end and returns a
  ;; (lazy, more on that later) sequence of the results
  ;; in the same order. Let's say we want to decrement
  ;; each element in a list of numbers by one

  (map dec '(1 1 2 3 5 8 13 21))

  ;; Let's say we then want to dec them again

  (->> '(1 1 2 3 5 8 13 21)
       (map dec)
       (map dec))

  ;; Hmmm, better to subtract by two, maybe?

  (map (fn [n] (- n 2)) '(1 1 2 3 5 8 13 21))

  ;; If you give `map` more collections to work on
  ;; it will repeatedly:
  ;; 1. pick the next item from each collection
  ;; 2. give them to the mapping function as arguments
  ;; 3. add the result to its return sequence
  ;; Until the shortest collection is exhausted

  (map + [1 2 3] '(0 2 4 6 8))
  (map (fn [n1 s n2] (str n1 ": " s "-" n2))
       (range)
       ["foo" "bar" "baz"]
       (range 2 -1 -1))

  ;; (We haven't talked much about `range`, it is a
  ;; function producing sequences of numbers. Given no
  ;; arguments it produces an infinite, watch out 😀,
  ;; sequence of integers
  ;; 0, 0+1, 0+2, 0+3, 0+4, 0+5, 0.6 ...
  ;; Good thing the other sequences got exhausted!)

  ;; A lot of the tasks you might solve with `for`
  ;; loops in other languages, are solved with `map`
  ;; in Clojure.

  ;; With other such ”for loopy” tasks you will
  ;; be wielding `reduce`. Unlike `map` it is not 
  ;; limited to producing results of the same length
  ;; or shape as the input collection. Instead it
  ;; accumulates a result of any shape. For instance,
  ;; it can create a string from a collection of
  ;; numbers

  (reduce (fn [acc n]
            (str acc ":" n))
          [1 1 2 3 5 8 13 21])

  ;; `reduce` will call the function with two
  ;;  arguments: the result of the last function
  ;;  call and the next number from the list. The
  ;;  start of the process is special, since then
  ;;  there are no results yet. `reduce` has two
  ;;  ways to deal with this, two arities to be
  ;;  specific. Called with two arguments, it
  ;;  uses the two first elements from the list
  ;;  for the first function call.
  ;;  Here's reducing the `+` function using the
  ;;  two-arity version of `reduce` 

  (reduce + [1 1 2 3 5 8 13 21])

  ;;  The process then starts with calling `+`
  ;;  like so

  (+ 1 1)

  ;; Giving `reduce` three arguments makes it us 
  ;; the second argument as the starting ”result”.

  (reduce + 100 [1 1 2 3 5 8 13 21])

  ;; You might have noticed that the `+` function
  ;; takes more (and less) than 2 arguments.

  (+)
  (+ 1)
  (+ 1 1)
  (+ 1 1 2 3 5 8 13 21)

  ;; `+` will take the first argument, if any, and
  ;; add it to ”the current” value (which is zero),
  ;; then the next argument and add that to the new
  ;; current value, and so on, and so forth, until
  ;; there is a result. This process sounds a bit
  ;; like we just described a reduce, right?
  ;; In fact it is.

  ;; If we were to implement the `+` function, how
  ;; could we do it? We could start by implementing
  ;; something that adds to numbers together, then
  ;; use it as as the reducing function with
  ;; `reduce`.
  ;; Of course, now we have the task of adding two
  ;; numbers together, without using the existing
  ;; `+` function... 🤔
  ;; Hmmm... Dodging the bootstrapping problem,
  ;; here's a totally insane way to do it:
  ;; We can use JavaScript to evaluate two numbers
  ;; joined by the string `"+"` 😜
  ;; Let's create a JS `ScriptEngine`!

  (import javax.script.ScriptEngineManager)
  (def js-engine (.getEngineByName (ScriptEngineManager.) "js"))
  (.eval js-engine "1+1")

  ;; Awesome, with this we can create an `add-two`
  ;; function

  (defn add-two [x y]
    (.eval js-engine (str x "+" y)))
  (add-two 1 1)

  ;; Unlike `+`, this one is not fully composable
  ;; with a higher order function like apply

  (apply add-two [])
  (apply add-two [1])
  (apply add-two [1 1])
  (apply add-two [1 1 2 3 5 8 13 21])

  ;; We need `add-many`. With `reduce` and our
  ;; `add-two` we can define `add-many` like so

  (defn add-many [& numbers]
    (reduce add-two numbers))
  ;; That does it, right?

  (apply add-many [1])
  (apply add-many [1 1])
  (apply add-many [1 1 2 3 5 8 13 21])

  ;; What about the zero-arity version of `+`, you
  ;; ask? Correct, that will blow up

  (add-many)

  ;; The built-in `+` function has a default ”current”
  ;; value of zero, remember? We can add that to
  ;; `add-many` in two ways: Either add a zero-arity
  ;; signature, or use the three-arity `reduce`. Let's
  ;; go for the latter option, since we are learning
  ;; about reduce here:

  (defn add* [& numbers]
    (reduce add-two 0 numbers))
  (add*)
  (add* 1 1 2 3)

  ;; BOOM.

  (apply add* [])
  (apply add* [1])
  (apply add* [1 1])
  (apply add* [1 1 2 3 5 8 13 21])

  ;; Apart from the lunatic way we add two numbers
  ;; this is very much like how `+` is implemented in
  ;; Clojure core. Check it out (in the output window):

  (source +)

  ;; Hmmm, well, they seem to be using multi-arity
  ;; function signatures instead, but anyway, that's
  ;; what `reduce` does as well 😀

  (source reduce)

  ;; There's one more thing with `reduce` we want to
  ;; mention. When writing reducing functions you can
  ;; stop the process before the input sequence is
  ;; exhausted, using the `reduced` function. Say we
  ;; want the input sequence as a string separated by 
  ;; `:`, as above, but stop when we see a `nil` item.
  ;; Here's the last version for comparison:

  (reduce (fn [acc n]
            (str acc ":" n))
          [1 1 2 3 5 8 nil 13 21])

  ;; We can short circuit the process by calling
  ;; `reduced` with the accumulated value when we
  ;; encounter a `nil` item

  (reduce (fn [acc n]
            (if (nil? n)
              (reduced acc)
              (str acc ":" n)))
          [1 1 2 3 5 8 nil 13 21])

  ;; Here is what is going on

  (doc reduced)

  ;; Reducing is a mighty important concept in Clojure
  ;; since it is a ”functional first” language. Or as
  ;; it is worded in this Functional Design episode
  ;; https://clojuredesign.club/episode/058-reducing-it-down/
  ;; ”Reducing functions are a backbone of functional
  ;; programming, because we don’t have mutation.”
  
  ;; In fact in Clojure reducing is so important that
  ;; Rich Hickey has added a whole library with reducers
  ;; packing even more punch
  ;; https://clojure.org/reference/reducers
  ;; The Functional Design duo, Nate Jones, and
  ;; Christoph Neumann have examined this library
  ;; a bit as well:
  ;; https://clojuredesign.club/episode/060-reduce-done-quick/
  ;; Amazing quote from that episode:
  ;;   “The seq abstraction, it’s rather lazy.”
  
  ;; We are not going down the rabbit hole of the
  ;; `reducers` library, though... 
  )

;; ... Instead we are picking up that Nate and
;; Christoph mention three super important concepts
;; in those two above quotes.
;; * immutability
;; * the `seq` abstraction
;; * laziness
;; They are related, and maybe it is best to start with
;; immutability...

(comment
  ;; = Immutability =
  ;; It is rather crazy that we have been talking about
  ;; Clojure for this long without discussing how
  ;; it encourages us to avoid mutating our data as
  ;; it is being processed.
  )

;; To be continued...

;; partial, comp
;; meta-data
;; comments
;; immutability
;; destructuring
;; atoms
;; nil, nil safety, nil punning
;; seqs
;; laziness
;; loop, recur

;; Learn much more Clojure at https://clojure.org/
;; There is also ClojureScript, the same wonderful language,
;; for JavaScript VMs: https://clojurescript.org

;; There is so much about Clojure not mentioned in this
;; short guide. https://clojure.org/ is where you
;; go for the complete story.

;; To get help with your Clojure questions, check these
;; resources out:
;; https://ask.clojure.org/
;; https://clojurians.net
;; https://clojureverse.org
;; https://www.reddit.com/r/Clojure/
;; https://exercism.io/tracks/clojure

;; And there are also many other resources, such as:
;; https://clojuredocs.org
;; https://clojure.org/api/cheatsheet

"File loaded. The REPL is ready to greet the world"

;; This guide is downloaded from:
;; https://github.com/BetterThanTomorrow/dram
;; Please consider contributing.
