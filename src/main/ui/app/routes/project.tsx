import * as Switch from "@radix-ui/react-switch";
import { useEffect } from "react";
import type { Route } from "./+types/project";

const flags = [
  {
    name: "NPCs",
    description: "Whether to allow NPC creation for games",
    key: "npc",
    enabled: true,
  },
  {
    name: "Publish",
    description: "Can a user publish their game for others to see",
    key: "publish",
    enabled: false,
  },
  {
    name: "Share to Edit",
    description: "Can a user share a game link with another user for editing",
    key: "share-to-edit",
    enabled: true,
  },
];

export default function Project({ params }: Route.ComponentProps) {
  useEffect(() => {
    console.log("Project ID: ", params.projectId);
  }, [params.projectId]);

  return (
    <main className="p-8">
      <section className="mx-auto max-w-4xl">
        <h1 className="mb-8 text-4xl font-light tracking-tight text-gray-800">
          Kaminel
        </h1>
        <div className="mb-4 flex flex-row items-center justify-between">
          <span className="relative">
            <input
              className="w-sm rounded-sm border border-gray-300 px-3 py-1.5 pl-7 text-sm text-gray-800 placeholder:text-gray-400"
              placeholder="Search for a flag"
            />
            <span className="absolute top-1/2 left-2 -translate-y-1/2 text-gray-400">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="size-4"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
                />
              </svg>
            </span>
          </span>
          <button className="flex cursor-pointer flex-row items-center justify-center gap-1 rounded-sm bg-orange-500 px-3 py-1.5 text-sm text-white shadow-xs transition-all hover:bg-orange-400 hover:shadow-sm active:bg-orange-600">
            <span>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="size-4"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M12 4.5v15m7.5-7.5h-15"
                />
              </svg>
            </span>
            <span className="tracking-tight">New Flag</span>
          </button>
        </div>
        <div className="overflow-x-auto rounded border border-gray-300">
          <table className="w-full table-fixed divide-y divide-gray-300">
            <thead className="bg-gray-50">
              <tr>
                <th className="w-2/5 p-4 text-left font-normal tracking-tight">
                  Flag
                </th>
                <th className="w-1/5 p-4 text-left font-normal tracking-tight">
                  Key
                </th>
                <th className="w-3/20 p-4 text-left font-normal tracking-tight">
                  Enabled
                </th>
                <th className="w-1/4 p-4 text-left font-normal tracking-tight">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-300">
              {flags.map((flag) => (
                <tr key={flag.key}>
                  <td className="p-4">
                    <div className="space-y-2">
                      <div className="text-xl font-semibold tracking-tight text-gray-800">
                        {flag.name}
                      </div>
                      <div className="max-w-full text-sm tracking-tight text-gray-800">
                        {flag.description}
                      </div>
                    </div>
                  </td>
                  <td className="p-4">
                    <span className="rounded-sm bg-orange-100 px-1.5 py-0.5 font-mono text-sm font-semibold text-orange-700">
                      {flag.key}
                    </span>
                  </td>
                  <td className="p-4">
                    <span>
                      <Switch.Root className="h-6 w-12 cursor-pointer rounded-full bg-gray-500 inset-shadow-xs transition-colors data-[state=checked]:bg-orange-500">
                        <Switch.Thumb className="block size-4 translate-x-1 rounded-full bg-white shadow-sm transition-transform will-change-transform data-[state=checked]:translate-x-7" />
                      </Switch.Root>
                    </span>
                  </td>
                  <td className="p-4">
                    <span className="flex flex-row items-center gap-4">
                      <button className="flex cursor-pointer flex-row items-center justify-center gap-1 rounded-sm border border-orange-500 bg-white px-3 py-1.5 text-sm text-orange-500 shadow-xs transition-all hover:bg-gray-50 hover:shadow-sm active:bg-gray-100">
                        <span>
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth={1.5}
                            stroke="currentColor"
                            className="size-4"
                          >
                            <path
                              strokeLinecap="round"
                              strokeLinejoin="round"
                              d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
                            />
                          </svg>
                        </span>
                        <span className="tracking-tight">Edit</span>
                      </button>
                      <button className="flex cursor-pointer flex-row items-center justify-center gap-1 rounded-sm border border-red-600 bg-red-600 px-3 py-1.5 text-sm text-white shadow-xs transition-all hover:border-red-500 hover:bg-red-500 hover:shadow-sm active:border-red-700 active:bg-red-700">
                        <span>
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth={1.5}
                            stroke="currentColor"
                            className="size-4"
                          >
                            <path
                              strokeLinecap="round"
                              strokeLinejoin="round"
                              d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
                            />
                          </svg>
                        </span>
                        <span className="tracking-tight">Delete</span>
                      </button>
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </main>
  );
}
