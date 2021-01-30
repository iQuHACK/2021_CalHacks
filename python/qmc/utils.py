import asyncio
import contextvars
import functools

async def to_thread(func, *args, **kwargs):
    """Backport of asyncio.to_thread for <3.9"""
    loop = asyncio.get_event_loop()
    ctx = contextvars.copy_context()
    func_call = functools.partial(ctx.run, func, *args, **kwargs)
    return await loop.run_in_executor(None, func_call)
