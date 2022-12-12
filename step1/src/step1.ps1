function IsAutomaticUpdateRunning {
    $au = sc.exe query wuauserv | find "4  RUNNING"
    if($au -like '*RUNNING*') {
        $regkey = 'Registry::HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Microsoft\Windows\WindowsUpdate\AU'
        $keyexists = Test-Path $regkey
        if($keyexists) {
            $reg = Get-ItemProperty -Path $regkey | Select-Object -ExpandProperty 'NoAutoUpdate'
            if($reg -eq 1) {
                return $false
            }
        }
        return $true
    }
    return $false
}


$Session = New-Object -ComObject "Microsoft.Update.Session"
$Searcher = $Session.CreateUpdateSearcher()
$historyCount = $Searcher.GetTotalHistoryCount()
$history = $Searcher.QueryHistory(0, $historyCount) | Select-Object Title, Date, @{name="Operation"; expression={switch($_.operation){ 1 {"Installation"}; 2 {"Uninstallation"}; 3 {"Other"} }}}, @{name="Status"; expression={switch($_.resultcode){ 1 {"In Progress"}; 2 {"Succeeded"}; 3 {"Succeeded With Errors"}; 4 {"Failed"}; 5 {"Aborted"} }}} | where Status -like 'Succeeded*'
# if an Uninstallation entry is not paired to a previous entry under Installation add
# | where Operation -neq 'Uninstallation'
# If Other means Failure add
# | where Operation -neq 'Other'

#test
#$historyx = @()
#$historyx += [PSCustomObject]@{
#    Title       = 'KB001'
#    Date        = '2022-12-11'
#    Operation   = 2
#    Status      = 2
#}
#$historyx += [PSCustomObject]@{
#    Title       = 'KB001'
#    Date        = '2022-12-11'
#    Operation   = 2
#    Status      = 2
#}
#$historyx += [PSCustomObject]@{
#    Title       = 'KB002'
#    Date        = '2022-12-11'
#    Operation   = 3
#    Status      = 2
#}
#$historyx += [PSCustomObject]@{
#    Title       = 'KB002'
#    Date        = '2022-12-11'
#    Operation   = 3
#    Status      = 0
#}
#$historyx += [PSCustomObject]@{
#    Title       = 'KB001'
#    Date        = '2022-12-10'
#    Operation   = 3
#    Status      = 2
#}
#$historyx += [PSCustomObject]@{
#    Title       = 'KB001'
#    Date        = '2022-12-10'
#    Operation   = 1
#    Status      = 2
#}
#$historyx += [PSCustomObject]@{
#    Title       = 'KB001'
#    Date        = '2022-12-09'
#    Operation   = 1
#    Status      = 2
#}
#$history = $historyx | Select-Object Title, Date, @{name="Operation"; expression={switch($_.operation){ 1 {"Installation"}; 2 {"Uninstallation"}; 3 {"Other"} }}}, @{name="Status"; #expression={switch($_.status){ 1 {"In Progress"}; 2 {"Succeeded"}; 3 {"Succeeded With Errors"}; 4 {"Failed"}; 5 {"Aborted"} }}} | where Status -like 'Succeeded*'
#end test


[System.Collections.ArrayList]$installedstack = @()
[System.Collections.ArrayList]$uninstalledstack = @()
foreach($entry in $history) {
    if($entry.Operation -eq 'Uninstallation') {
        $uninstalledstack += $entry
        continue
    }
    
    $wasuninstalled = $false
    for ($i = 0; $i -lt $uninstalledstack.count; $i++)
    {
        if ($uninstalledstack[$i].Title -eq $entry.title)
        {
            $uninstalledstack.RemoveAt($i)
            $i--
            $wasuninstalled = $true
            break
        }
    }
    
    if(-Not $wasuninstalled) {
        $installedstack += $entry
    }
}

$installedstack