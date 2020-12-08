local log = require("log")
local Api = require("coreApi")
local json = require("json")
local http = require("http")

function Split(szFullString, szSeparator)
        local nFindStartIndex = 1
        local nSplitIndex = 1
        local nSplitArray = {}
        while true do
           local nFindLastIndex = string.find(szFullString, szSeparator, nFindStartIndex)
           if not nFindLastIndex then
            nSplitArray[nSplitIndex] = string.sub(szFullString, nFindStartIndex, string.len(szFullString))
            break
           end
           nSplitArray[nSplitIndex] = string.sub(szFullString, nFindStartIndex, nFindLastIndex - 1)
           nFindStartIndex = nFindLastIndex + string.len(szSeparator)
           nSplitIndex = nSplitIndex + 1
        end
        return nSplitArray
end

function ReceiveFriendMsg(CurrentQQ, data)
    return 1
end
function ReceiveGroupMsg(CurrentQQ, data)
    local list = Split(data.Content," ")
    local body = {
	pool = list[2],
	qq = data.FromUserId
}
	log.notice("From Lua SendMsg Ret-->%s", json.encode(body))
    if (list[1] == "抽卡") then
	response, error_message =
		http.post(
			"http://192.168.15.1:8086/Arknights/chouKa",
			{
				body = json.encode(body),
				headers = 
				{
					["Accept"] = "*/*",
					["Content-Type"] = "application/json"
				}	
			}
		)
	local html = response.body
	log.notice("From Lua SendMsg Ret-->%s", html)
	luaRes =
		Api.Api_SendMsg(
            CurrentQQ,
            {
                toUser = data.FromGroupId,
                sendToType = 2,
                sendMsgType = "TextMsg",
                groupid = 0,
                content = html,
                atUser = data.FromUserId
            }
        )
    elseif (list[1] == "十连") then
	response, error_message =
		http.post(
			"http://192.168.15.1:8086/Arknights/shiLian",	
			{
				body = json.encode(body),
				headers = 
				{
					["Accept"] = "*/*",
					["Content-Type"] = "application/json"
				}
		    }
		)
	local html = response.body
        luaRes =
            Api.Api_SendMsg(
            CurrentQQ,
            {
                toUser = data.FromGroupId,
                sendToType = 2,
                sendMsgType = "TextMsg",
                groupid = 0,
                content = html,
                atUser = data.FromUserId
            }
        )
		log.notice("From Lua SendMsg Ret-->%s", html)
	end
    return 1
end
function ReceiveEvents(CurrentQQ, data, extData)
    return 1
end

