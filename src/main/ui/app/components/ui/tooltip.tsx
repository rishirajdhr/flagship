import * as RadixTooltip from "@radix-ui/react-tooltip";
import React, { useState } from "react";

interface TooltipProps {
  content: string;
  children: React.ReactNode;
}

function Tooltip(props: TooltipProps) {
  const [open, setOpen] = useState(false);

  return (
    <RadixTooltip.Root open={open} onOpenChange={setOpen} delayDuration={100}>
      <RadixTooltip.Trigger asChild>{props.children}</RadixTooltip.Trigger>
      <RadixTooltip.Portal forceMount={true}>
        <RadixTooltip.Content
          side="top"
          sideOffset={4}
          align="center"
          className="rounded-md bg-gray-800 px-3 py-1.5 text-sm tracking-tight text-gray-100 shadow-sm transition-all duration-200 data-[side=top]:-translate-y-1 data-[state=closed]:opacity-0 data-[state=delayed-open]:translate-y-0 data-[state=delayed-open]:opacity-100"
        >
          {props.content}
          <RadixTooltip.Arrow className="fill-gray-800" />
        </RadixTooltip.Content>
      </RadixTooltip.Portal>
    </RadixTooltip.Root>
  );
}

const { Provider } = RadixTooltip;

export { Tooltip, type TooltipProps, Provider as TooltipProvider };
