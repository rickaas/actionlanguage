[
   Module                            -- KW["module"] _1 _2,
   Module.2:iter-star                -- _1,
   FunctionDecl                      -- KW["function"] _1 _2 KW["("] _3 KW[")"] KW["{"] _4 KW["}"],
   FunctionDecl.3:iter-star-sep      -- _1 KW[","],
   FunctionDecl.4:iter-star          -- _1,
   FunctionDeclVoid                  -- KW["function"] KW["void"] _1 KW["("] _2 KW[")"] KW["{"] _3 KW["}"],
   FunctionDeclVoid.2:iter-star-sep  -- _1 KW[","],
   FunctionDeclVoid.3:iter-star      -- _1,
   Argdef                            -- _1 _2,
   Entity                            -- KW["entity"] _1 KW["{"] _2 KW["}"],
   Entity.2:iter-star                -- _1,
   Main                              -- KW["main"] _1 KW[";"],
   Property                          -- _1 KW[":"] _2,
   Declaration                       -- _1 _2 _3 KW[";"],
   Declaration.3:opt                 -- _1,
   Declaration.3:opt.1:seq           -- KW[":="] _1,
   If                                -- KW["if"] KW["("] _1 KW[")"] KW["{"] _2 KW["}"],
   If.2:iter-star                    -- _1,
   IfElse                            -- KW["if"] KW["("] _1 KW[")"] KW["{"] _2 KW["}"] KW["else"] KW["{"] _3 KW["}"],
   IfElse.2:iter-star                -- _1,
   IfElse.3:iter-star                -- _1,
   Return                            -- KW["return"] KW[";"],
   ReturnValue                       -- KW["return"] _1 KW[";"],
   StatementBlock                    -- V  [V vs=2 [KW["{"] _1] KW["}"]],
   StatementBlock.1:iter-star        -- _1,
   TryFinally                        -- KW["try"] KW["{"] _1 KW["}"] KW["finally"] KW["{"] _2 KW["}"],
   TryFinally.1:iter-star            -- _1,
   TryFinally.2:iter-star            -- _1,
   While                             -- KW["while"] KW["("] _1 KW[")"] KW["{"] _2 KW["}"],
   While.2:iter-star                 -- _1,
   ExprStatement                     -- _1 KW[";"],
   FunctionArguments                 -- KW["("] _1 KW[")"],
   FunctionArguments.1:iter-star-sep -- _1 KW[","],
   FunctionCall                      -- _1 _2 _3,
   FunctionCall.1:opt                -- _1,
   FunctionCall.1:opt.1:seq          -- _1 KW["."],
   GlobalCall                        -- KW["global"] KW["."] _1 _2,
   DebugSystemCall                   -- KW["debug"] KW["."] _1 _2,
   SystemCall                        -- KW["system"] KW["."] _1 _2,
   PropertyAccess                    -- _1 KW["."] _2,
   Getter                            -- _1,
   AssignVar                         -- _1 KW[":="] _2 KW[";"],
   AssignProperty                    -- _1 KW[":="] _2 KW[";"],
   NewEntity                         -- KW["new"] _1,
   Var                               -- _1,
   Int                               -- _1,
   String                            -- _1,
   Bool                              -- _1,
   Times                             -- _1 KW["*"] _2,
   Div                               -- _1 KW["/"] _2,
   Mod                               -- _1 KW["%"] _2,
   Plus                              -- _1 KW["+"] _2,
   Minus                             -- _1 KW["-"] _2,
   Eq                                -- _1 KW["=="] _2,
   LT                                -- _1 KW["<"] _2,
   GT                                -- _1 KW[">"] _2,
   LTEq                              -- _1 KW["<="] _2,
   GTEq                              -- _1 KW[">="] _2,
   NEq                               -- _1 KW["!="] _2,
   Type                              -- _1
]