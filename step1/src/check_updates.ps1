$Session = New-Object -ComObject "Microsoft.Update.Session"
$Searcher = $Session.CreateUpdateSearcher()
$historyCount = $Searcher.GetTotalHistoryCount()
$Searcher.QueryHistory(0, $historyCount) | Select-Object Title, Date,
    @{name='ResultCode';
      expression={switch($_.ResultCode){
        0 {'Not Started'};
        1 {'In Progress'}; 
        2 {'Success'};
        3 {'Success with Errors'};
        4 {'Failed'};
        5 {'Aborted'}
      }}
    }
