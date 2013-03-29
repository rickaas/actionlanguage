[
    
    Module                           
    --   V [    H [KW["module"] _1]   _2    ],
    Module.2:iter-star               
    -- V [_1],
    FunctionDecl                     
    -- V is=4 [  H [ KW["function"] _1 _2 KW["("] _3 KW[")"] KW["{"] ] V[_4] ] KW["}"],
%%    FunctionDecl.3:iter-star-sep     -- _1 KW[","],
    FunctionDecl.4:iter-star         -- V [_1],
    FunctionDeclVoid                 
    -- V is=4 [  H [ KW["function"] KW["void"] _1 KW["("] _2 KW[")"] KW["{"] ] V[_3] ] KW["}"],
%%    FunctionDeclVoid.2:iter-star-sep -- _1 KW[","],
%%    FunctionDeclVoid.3:iter-star     -- _1,
    Argdef                           -- H[ _1 _2 ],
    Entity                           
    -- V is=4 [  H [ KW["entity"] _1 KW["{"] ] _2   ] KW["}"],
%%    Entity.2:iter-star               -- _1,
    Main                             -- H [  KW["main"] _1 KW[";"]  ],
    Property                         -- H [  _1 KW[":"] _2  ],
    %% statements
    Declaration                      -- H [  _1 _2 _3 KW[";"]  ], %% _3 is optional
    Declaration.3:opt -- H [ KW[":="] _1 ], %% var declaration with initial value
    If                                
    -- V[  
        H[ KW["if"] KW["("] _1 KW[")"]  ]
        V is=2 [  KW["{"] _2  ]
        V [ KW["}"]  ]
        ],
    IfElse                               
    -- V[  
        H[ KW["if"] KW["("] _1 KW[")"]  ] 
        V is=2 [  KW["{"] _2  ] 
        V [ KW["}"] KW["else"] ] 
        V is=2 [  KW["{"] _3  ]
        KW["}"]  
       ],
    Return                           -- H [  KW["return"] KW[";"]  ],
    ReturnValue                      -- H [  KW["return"] _1 KW[";"]  ],
    StatementBlock                   -- V[   V is=2 [ KW["{"] _1 ] KW["}"]   ],
    TryFinally                       
    -- V[
         KW["try"] 
         V is=2 [  KW["{"] _1  ]
         V[  KW["}"] KW["finally"] ]
         V is=2 [  KW["{"] _2  ]
         KW["}"]
        ],
%%    TryFinally.1:iter-star           -- _1,
%%    TryFinally.2:iter-star           -- _1,
    While                            
    -- V[  
           H[ KW["while"] KW["("] _1 KW[")"] ]
           V is=2 [  KW["{"]  _2  ]
           KW["}"]  
         ],
%%    While.2:iter-star                -- _1,
    ExprStatement                     -- H [  _1 KW[";"]  ],
    FunctionArguments                 -- H [  KW["("] _1 KW[")"]  ],

   FunctionCall                      -- H [  _1 _2 _3  ],
   FunctionCall.1:opt                -- _1 KW["."],
%%   FunctionCall.1:opt.1:seq          -- _1 KW["."],
%%         FunctionCall                     -- H hs=0 [  _1 KW["."] _2 _3  ],
   GlobalCall                       -- H hs=0 [  KW["global"] KW["."] _1 _2  ],
   DebugSystemCall                  -- H hs=0 [  KW["debug"] KW["."] _1 _2  ],
   SystemCall                       -- H hs=0 [  KW["system"] KW["."] _1 _2  ],

    AssignVar                        -- H [  _1 KW[":="] _2 KW[";"]  ],
   


   %% NewEntity                         -- KW["new"] _1,
   %% Var                               -- _1,
   %% Int                               -- _1,
   %% String                            -- _1,
   %% Bool                              -- _1,
   %% Times                             -- _1 KW["*"] _2,
   %% Div                               -- _1 KW["/"] _2,
   %% Mod                               -- _1 KW["%"] _2,
   %% Plus                              -- _1 KW["+"] _2,
   %% Minus                             -- _1 KW["-"] _2,
   %% Eq                                -- _1 KW["=="] _2,
   %% LT                                -- _1 KW["<"] _2,
   %% GT                                -- _1 KW[">"] _2,
   %% LTEq                              -- _1 KW["<="] _2,
   %% GTEq                              -- _1 KW[">="] _2,
   %% NEq                               -- _1 KW["!="] _2,
   Type                              -- _1
]